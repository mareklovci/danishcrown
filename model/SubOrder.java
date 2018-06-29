package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class SubOrder implements Serializable {

    private static final long serialVersionUID = -8127897371374870895L;
    private double            weight;
    private double            weightMargin;
    private LocalDate         loadingDate;
    private int               loadingDuration;
    private LocalTime         loadingTime;

    // Link fields
    private Order             order;
    private Trailer           trailer;
    private Schedule          schedule;

    public SubOrder(Trailer trailer, double weight, double weightMargin, LocalDate loadingDate, int loadingDuration) {
        this.trailer = trailer;
        this.weight = weight;
        this.weightMargin = weightMargin;
        this.loadingDate = loadingDate;
        setLoadingDuration(loadingDuration);
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Trailer getTrailer() {
        return trailer;
    }

    public void setTruck(Trailer trailer) {
        this.trailer = trailer;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public double getWeightMargin() {
        return weightMargin;
    }

    public void setWeightMargin(double weightMargin) {
        this.weightMargin = weightMargin;
    }

    public LocalDate getLoadingDate() {
        return loadingDate;
    }

    public LocalTime getLoadingTime() {
        return loadingTime;
    }

    public void setLoadingTime(LocalTime time) {
        this.loadingTime = time;
    }

    public int getLoadingDuration() {
        return loadingDuration;
    }

    public void setLoadingDuration(int loadingDuration) {
        this.loadingDuration = loadingDuration;
    }

    public LocalTime getLoadingEndTime() {
        return this.loadingTime.plusMinutes(this.loadingDuration);
    }

}
