package storage;

import java.util.ArrayList;
import java.util.List;

import model.Card;
import model.Dock;
import model.Order;
import model.Schedule;

public class Storage {

    private static List<Schedule> schedules = new ArrayList<>();
    private static List<Card>     cards     = new ArrayList<>();
    private static List<Dock>     docks     = new ArrayList<>();
    private static List<Order>    orders    = new ArrayList<>();

    public static List<Schedule> getSchedules() {
        return new ArrayList<>(schedules);
    }

    public static void saveSchedule(Schedule schedule) {
        schedules.add(schedule);
    }

    public static List<Card> getCards() {
        return new ArrayList<>(cards);
    }

    public static void saveCard(Card card) {
        cards.add(card);
    }

    public static List<Dock> getDocks() {
        return new ArrayList<>(docks);
    }

    public static void saveDock(Dock dock) {
        docks.add(dock);
    }

    public static List<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    public static void saveOrder(Order order) {
        orders.add(order);
    }

}
