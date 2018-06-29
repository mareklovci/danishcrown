package gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Driver;
import model.Schedule;
import model.SubOrder;
import service.Service;

public class DriverPane extends Stage {
    private final Driver     driver;
    private TreeView<String> tvSchedules;
    private TextField        txWeight;

    public DriverPane(Driver driver) {
        this.driver = driver;

        setTitle("Driver Pane");
        GridPane pane = new GridPane();
        initContent(pane);
        Scene scene = new Scene(pane);
        setScene(scene);
    }

    private void initContent(GridPane pane) {
        // pane.setGridLinesVisible(true);
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(20);

        addScheduleTree();

        Label lblName = new Label("Name: ");
        pane.add(lblName, 0, 0);

        Label lblName1 = new Label(driver.getName());
        pane.add(lblName1, 1, 0);

        Label lblPhoneNum = new Label("Phone #: ");
        pane.add(lblPhoneNum, 0, 1);

        Label lblPhoneNum1 = new Label(driver.getPhoneNumber());
        pane.add(lblPhoneNum1, 1, 1);

        Label lblTruckNum = new Label("Truck #: ");
        pane.add(lblTruckNum, 0, 2);

        Label lblTruckNum1 = new Label(driver.getTruck().getNumber());
        pane.add(lblTruckNum1, 1, 2);

        Label lblTrailerNum = new Label("Trailer #: ");
        pane.add(lblTrailerNum, 0, 3);

        Label lblTrailerNum1 = new Label(driver.getTruck().getTrailer().getId());
        pane.add(lblTrailerNum1, 1, 3);

        Label lblWeight = new Label("Weight: ");
        pane.add(lblWeight, 0, 4);

        this.txWeight = new TextField();
        pane.add(txWeight, 1, 4);

        Button btnNewEntry = new Button("Log Out");
        pane.add(btnNewEntry, 0, 5);
        btnNewEntry.setOnAction(event -> logOutAction());

        pane.add(tvSchedules, 0, 6, 5, 3);
    }

    private void logOutAction() {
        try {
            Schedule s = Service.getSchedules(driver).get(0);
            s.getTrailer().setWeight(Integer.parseInt(txWeight.getText().trim()));

            double actualWeight = Double.parseDouble(this.txWeight.getText().trim());
            SubOrder sOrder = this.driver.getTruck().getTrailer().getSchedules().get(0).getSubOrder();
            if (Service.outOfMargin(actualWeight, sOrder.getWeight(), sOrder.getWeightMargin())) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning message!");
                alert.setHeaderText("Info");
                alert.setContentText(
                        "Trailer weight is out of margin, please return to the loading area to fix issue.\nPlease reload the trailer!");
                alert.showAndWait();
            } else {
                s.clear();
                close();
            }
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Weight!");
            alert.setHeaderText("Info");
            alert.setContentText("Weight is not a number. Type a proper number");
            alert.showAndWait();
        }

    }

    private void addScheduleTree() {
        TreeItem<String> root = new TreeItem<String>();
        for (Schedule s : Service.getSchedules(driver)) {
            root.getChildren().add(TreeViewService.createTreeItemDriver(s));
        }

        this.tvSchedules = new TreeView<String>(root);
        this.tvSchedules.setShowRoot(false);
    }

}