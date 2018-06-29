package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Trailer implements Serializable {

    private static final long serialVersionUID = -5673100625618639835L;
    private String            id;
    private double            weight;
    private double            trailerWeight;

    // Link fields
    private Truck             truck;
    private List<ProductType> equipment        = new ArrayList<>();
    private List<Schedule>    schedule         = new ArrayList<>();

    public Trailer(String id, double trailerWeight) {
        this.id = id;
        this.trailerWeight = trailerWeight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public String getId() {
        return id;
    }

    public double getTrailerWeight() {
        return trailerWeight;
    }

    public List<ProductType> getEquipment() {
        return new ArrayList<>(equipment);
    }

    public void addEquipment(ProductType equipment) {
        this.equipment.add(equipment);
    }

    public List<Schedule> getSchedules() {
        return new ArrayList<>(schedule);
    }

    public void addSchedule(Schedule schedule) {
        this.schedule.add(schedule);
    }

    public void removeSchedule(Schedule schedule) {
        this.schedule.remove(schedule);
    }

}
