package gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Dock;
import model.Schedule;
import service.Service;

public class EmployeePane extends Stage {

    private TreeView<String> tvSchedules;
    private ComboBox<Dock>   chooseDock;

    public EmployeePane() {
        setTitle("Employee Pane");
        GridPane pane = new GridPane();
        initContent(pane);
        Scene scene = new Scene(pane);
        setScene(scene);
    }

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(20);

        Label lblDock = new Label("Dock #: ");
        pane.add(lblDock, 0, 0);

        chooseDock = new ComboBox<>();
        chooseDock.getItems().addAll(Service.getDocks());
        chooseDock.valueProperty().addListener((o, oldValue, newValue) -> addScheduleTree(newValue));
        pane.add(chooseDock, 1, 0);

        Button btnLoaded = new Button("Loaded");
        pane.add(btnLoaded, 0, 2);
        btnLoaded.setOnAction(event -> loaded());

        Button btnLogOut = new Button("Log out");
        pane.add(btnLogOut, 1, 2);
        btnLogOut.setOnAction(event -> logOut());

        Button btnBreakDown = new Button("Dock broke down");
        pane.add(btnBreakDown, 2, 2);
        btnBreakDown.setOnAction(event -> breakDown());

        this.tvSchedules = new TreeView<String>(new TreeItem<String>());
        this.tvSchedules.setShowRoot(false);
        pane.add(tvSchedules, 0, 3, 4, 3);
    }

    private void breakDown() {
        if (chooseDock.getSelectionModel().getSelectedIndex() < 0) {
            return;
        }
        Dock selectedDock = chooseDock.getSelectionModel().getSelectedItem();
        Service.setDockAvailability(selectedDock, false);
        Service.organizeSchedules();

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Mail been sent");
        alert.setHeaderText("Info");
        String s = "The administartor has been informed that the dock has broked down";
        alert.setContentText(s);
        alert.showAndWait();
    }

    private void logOut() {
        close();
    }

    private void loaded() {
        if (chooseDock.getSelectionModel().getSelectedIndex() < 0) {
            return;
        }
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("SMS been sent");
        alert.setHeaderText("Info");
        String s = "Driver will be informed about loading";
        alert.setContentText(s);
        alert.showAndWait();
    }

    private void addScheduleTree(Dock dock) {
        TreeItem<String> root = this.tvSchedules.getRoot();
        root.getChildren().clear();
        for (Schedule s : Service.getSchedules(dock)) {
            root.getChildren().add(TreeViewService.createTreeItemEmployee(s));
        }
    }

}
