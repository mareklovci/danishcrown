package gui;

import javafx.scene.control.TreeItem;
import model.Schedule;

public class TreeViewService {

    public static TreeItem<String> createTreeItemDriver(Schedule s) {

        TreeItem<String> item = new TreeItem<String>("Schedule");
        item.getChildren().add(new TreeItem<String>("Dock num: " + s.getDock()));
        item.getChildren().add(new TreeItem<String>("Date: " + s.getSubOrder().getLoadingDate()));
        item.getChildren().add(new TreeItem<String>("Weight: " + s.getSubOrder().getWeight() + "kg"));
        item.getChildren().add(new TreeItem<String>("Loading time start: " + s.getSubOrder().getLoadingTime()));
        item.getChildren()
                .add(new TreeItem<String>("Loading duration: " + s.getSubOrder().getLoadingDuration() + "min"));
        item.getChildren().add(new TreeItem<String>("Loading time end: "
                + s.getSubOrder().getLoadingTime().plusMinutes(s.getSubOrder().getLoadingDuration())));

        return item;
    }

    public static TreeItem<String> createTreeItemEmployee(Schedule s) {

        TreeItem<String> schedule = new TreeItem<String>("Schedule " + s.getSubOrder().getOrder().getId());
        schedule.getChildren().add(new TreeItem<String>("Trailer ID: " + s.getSubOrder().getTrailer().getId()));
        TreeItem<String> subOrder = new TreeItem<String>("SubOrder No: " + s.getSubOrder().getOrder().getId());
        schedule.getChildren().add(subOrder);
        subOrder.getChildren().add(new TreeItem<String>("Weight: " + s.getSubOrder().getWeight()));
        subOrder.getChildren().add(new TreeItem<String>("Loading Time: " + s.getSubOrder().getLoadingTime()));

        return schedule;
    }
}
