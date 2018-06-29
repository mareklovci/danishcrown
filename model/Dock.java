package model;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Dock implements Serializable {

    private static final long serialVersionUID = -8404423780682109357L;
    private String            number;
    private ProductType       productType;
    private boolean           available;

    // Link fields
    private Deque<Schedule>   schedules        = new LinkedList<>();

    private static LocalTime  startTime        = LocalTime.of(8, 0);

    public Dock(String number, ProductType productType) {
        this.number = number;
        this.productType = productType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setTransportEquipment(ProductType transportEquipment) {
        this.productType = transportEquipment;
    }

    public List<Schedule> getSchedules() {
        return new ArrayList<Schedule>(schedules);
    }

    public void addSchedule(Schedule schedule) {
        schedule.getSubOrder().setLoadingTime(nextAvailableTime());
        schedules.addLast(schedule);
    }

    public void removeSchedule(Schedule schedule) {
        schedules.remove(schedule);
    }

    public LocalTime nextAvailableTime() {
        int totalMinutes = 0;

        for (Schedule s : schedules) {
            totalMinutes += s.getSubOrder().getLoadingDuration() + 120;
        }

        return startTime.plusMinutes(totalMinutes);
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
        if (available == false) {
            while (schedules.size() > 0) {
                schedules.pop().clear();
            }
        }
    }

    @Override
    public String toString() {
        return number;
    }

}
