package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Driver;
import service.Service;

public class MainApp extends Application
{

    private Text txtStatus;
    private TextField txfPassword;

    private ComboBox<String> userType;
    private ComboBox<Driver> chooseDriver;

    public static void main(String[] args)
    {
        Service.initStorage();
        Service.organizeSchedules();
        Application.launch(args);
    }

    @Override
    public void start(Stage stage)
    {
        System.setProperty("glass.accessible.force", "false");
        stage.setTitle("Control Panel");
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private void initContent(GridPane pane)
    {
        // pane.setGridLinesVisible(true);
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(20);

        VBox vbox = new VBox(30);
        pane.add(vbox, 0, 0);
        vbox.setAlignment(Pos.CENTER);

        // header label
        Label lblHeader = new Label("Welcome to DC!");
        vbox.getChildren().add(lblHeader);
        lblHeader.setTextFill(Color.BLACK);
        lblHeader.setFont(Font.font("Calibri", FontWeight.BOLD, 36));

        // comboBox for user selection
        userType = new ComboBox<String>();
        userType.getItems().addAll("Driver", "Employee");
        userType.setValue("Driver");
        pane.add(userType, 0, 1);
        userType.setOnAction(event -> this.setDifferentUserType());

        // user name and password fields
        Label lblUserName = new Label("Username:");
        pane.add(lblUserName, 0, 2);

        chooseDriver = new ComboBox<Driver>();
        chooseDriver.getItems().addAll(Service.getDrivers());
        chooseDriver.setValue(Service.getDrivers().get(0));
        pane.add(chooseDriver, 1, 2);

        Label lblPassword = new Label("Password:");
        pane.add(lblPassword, 0, 3);

        this.txfPassword = new TextField();
        pane.add(txfPassword, 1, 3);
        txfPassword.setText("123456");

        // Driver Pane Button
        Button btnLogIn = new Button("Log In");
        pane.add(btnLogIn, 0, 4);
        btnLogIn.setOnAction(event -> this.openPaneAction());

        // status text
        this.txtStatus = new Text("");
        pane.add(this.txtStatus, 0, 5);
        this.txtStatus.setFont(Font.font("Calibri", FontWeight.NORMAL, 20));
        this.txtStatus.setFill(Color.FIREBRICK);

    }

    private void setDifferentUserType()
    {
        if (userType.getSelectionModel().getSelectedIndex() == 1) {
            chooseDriver.setDisable(true);
            txfPassword.setDisable(true);
        } else {
            chooseDriver.setDisable(false);
            txfPassword.setDisable(false);
        }
    }

    private void openPaneAction()
    {
        DriverPane driverPane = new DriverPane(chooseDriver.getSelectionModel().getSelectedItem());
        EmployeePane employeePane = new EmployeePane();

        // Shows pane selected on ComboBox
        if (userType.getSelectionModel().getSelectedIndex() == 1) {
            employeePane.show();
        } else {
            if (Service.verifyUser(
                    chooseDriver.getSelectionModel().getSelectedItem().getName().toLowerCase(),
                    txfPassword.getText()) != null) {
                driverPane.show();
                this.txtStatus.setText("You are logged in!");
            } else {
                this.txtStatus.setText("Invalid password or username");
            }
        }
    }
}