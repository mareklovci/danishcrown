package service;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import model.Card;
import model.Dock;
import model.Driver;
import model.Order;
import model.ProductType;
import model.Schedule;
import model.SubOrder;
import model.Trailer;
import model.Truck;
import storage.Storage;

public class Service {

    public static List<Schedule> getSchedules(Driver d) {
        assert d != null;
        return d.getTruck().getTrailer().getSchedules();
    }

    public static List<Schedule> getSchedules(Dock d) {
        assert d != null;
        return d.getSchedules();
    }

    public static Schedule createSchedule(SubOrder subOrder, Dock dock, Trailer trailer) {
        assert subOrder != null;
        assert dock != null;
        assert trailer != null;
        Schedule schedule = new Schedule(dock, trailer, subOrder);
        Storage.saveSchedule(schedule);
        return schedule;
    }

    public static Driver createDriver(String name, String phoneNumber, String email) {
        assert name != null;
        assert phoneNumber != null;
        assert email != null;
        Driver driver = new Driver(name, phoneNumber, email);
        return driver;
    }

    public static Truck createTruck(String number) {
        assert number != null;
        Truck truck = new Truck(number);
        return truck;
    }

    public static Trailer createTrailer(String id, double trailerWeight) {
        assert id != null;
        assert trailerWeight < 0;
        Trailer trailer = new Trailer(id, trailerWeight);
        return trailer;
    }

    /*
     * calls the other main organizeSchedule method
     */
    public static void organizeSchedules() {
        Service.organizeSchedules(Storage.getOrders());
    }

    /*
     * collects all the suborders from all the orders filters the suborders that
     * are from today and that are not scheduled check every type of the docks
     * for every type it gets the dock that are of that type schedules them 1 by
     * 1
     */
    public static void organizeSchedules(List<Order> orders) {
        // Get all suborders
        List<SubOrder> subOrders = new ArrayList<>();
        orders.stream().forEach(order -> subOrders.addAll(order.getSubOrders()));

        // Filter orders for today
        List<SubOrder> today = subOrders.stream().filter(s -> s.getLoadingDate().equals(LocalDate.now()))
                .filter(s -> s.getSchedule() == null).collect(Collectors.toList());

        List<ProductType> types = Arrays.asList(ProductType.values());

        Comparator<Dock> dockComparator = new Comparator<Dock>() {
            @Override
            public int compare(Dock d1, Dock d2) {
                return d1.nextAvailableTime().compareTo(d2.nextAvailableTime());
            }
        };

        // For every type
        types.stream().forEach(type -> {

            // Get all the appropriate docks
            List<Dock> docks = Storage.getDocks().stream().filter(d -> d.getProductType() == type)
                    .collect(Collectors.toList());

            // Get all suborders of the specific type
            today.stream().filter(s -> s.getOrder().getTypeOfProduct() == type).forEach(so -> {
                docks.sort(dockComparator);
                Dock earliestDock = docks.get(0);

                Service.createSchedule(so, earliestDock, so.getTrailer());
            });

        });

    }

    public static void initStorage() {

        Driver[] driver = new Driver[15];
        Card[] card = new Card[15];
        Truck[] truck = new Truck[15];
        Trailer[] trailer = new Trailer[15];
        for (int i = 0; i < 15; i++) {
            driver[i] = Service.createDriver("Driver " + i, "1232334" + i, "driver" + i + "@dc.com");
            card[i] = Service.createCard(driver[i], "driver " + i, "123456");
            truck[i] = Service.createTruck("10" + 1);
            trailer[i] = Service.createTrailer("" + i, 150);
            Service.assignDriver(driver[i], truck[i], trailer[i]);

        }

        Order[] orders = new Order[15];

        for (int i = 0; i < 15; i++) {
            int randomProductType = new Random().nextInt(3);

            int secondRandom = new Random().nextInt(3);
            orders[i] = Service.createOrder("" + i, ProductType.values()[randomProductType], 1000);
            orders[i].createSubOrder(trailer[i], 500, 7, LocalDate.now(), (secondRandom + 1) * 10);

            /**
             * first will be trailer 1 Second suborder will be assigned to
             * 39,38... so they will never meet in the middle
             */

            orders[i].createSubOrder(trailer[14 - i], 500, 7, LocalDate.now(), (randomProductType + 1) * 10);
        }

        Service.createDock("1", ProductType.Boxed);
        Service.createDock("2", ProductType.Boxed);
        Service.createDock("3", ProductType.ChrismasTree);
        Service.createDock("4", ProductType.ChrismasTree);
        Service.createDock("5", ProductType.Palletised);
    }

    public static Dock createDock(String number, ProductType type) {
        assert number != null;
        assert type != null;
        Dock dock = new Dock(number, type);
        Storage.saveDock(dock);
        return dock;
    }

    public static List<Schedule> getAllSchedules() {
        return Storage.getSchedules();
    }

    public static Order createOrder(String id, ProductType type, double weight) {
        assert id != null;
        assert type != null;
        assert weight > 0;
        Order order = new Order(id, type, weight);
        Storage.saveOrder(order);
        return order;
    }

    public static SubOrder createSubOrder(Order order, Trailer trailer, double weight, double weightMargin,
            LocalDate loadingDate, int loadingTime) {
        assert order != null;
        assert trailer != null;
        return order.createSubOrder(trailer, weight, weightMargin, loadingDate, loadingTime);
    }

    public static Card createCard(Driver driver, String username, String password) {
        assert driver != null;
        Card card = driver.createCard(username, password);
        Storage.saveCard(card);
        return card;
    }

    public static List<Dock> getDocks() {
        return Storage.getDocks();
    }

    /*
     * checks if the provided username and passowrd are correct in the storage
     */
    public static Card verifyUser(String username, String password) {

        if (username == null || password == null)
            return null;

        for (Card c : Storage.getCards()) {
            if (username.equals(c.getUsername()) && password.equals(c.getPassword())) {
                return c;
            }
        }
        return null;

    }

    public static List<Driver> getDrivers() {
        List<Driver> drivers = new ArrayList<>();

        for (Card c : Storage.getCards()) {
            drivers.add(c.getDriver());
        }
        return drivers;
    }

    /*
     * sets connections between driver,truck and trailer
     */
    public static void assignDriver(Driver driver, Truck truck, Trailer trailer) {
        assert driver != null;
        assert truck != null;
        assert trailer != null;
        driver.setTruck(truck);
        truck.setDriver(driver);

        truck.setTrailer(trailer);
        trailer.setTruck(truck);
    }

    /*
     * removes the schedule that is ready to go on trip
     */
    public static void loaded(Schedule schedule, Dock dock) {
        assert schedule != null;
        assert dock != null;
        dock.removeSchedule(schedule);
    }

    public static void setDockAvailability(Dock dock, boolean availability) {
        assert dock != null;
        dock.setAvailable(availability);
        if (availability == false) {
            Mail.sendMail("petoknm@gmail.com", "Dock Alert", "Dock " + dock + " availability has changed!");
            organizeSchedules();
        }
    }

    /*
     * puts the storages objects in a file.The specific objects use serializable
     */
    public static void putStorageToFile() {
        try (FileOutputStream streamOut = new FileOutputStream("src/Storage.dat")) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(streamOut);
            for (Schedule s : Service.getAllSchedules()) {
                objectOutputStream.writeObject(s);
            }
            for (Card c : Storage.getCards()) {
                objectOutputStream.writeObject(c);
            }
            for (Dock d : Service.getDocks()) {
                objectOutputStream.writeObject(d);
            }
            for (Order o : Storage.getOrders()) {
                objectOutputStream.writeObject(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * reads the storage from file
     */
    public static void readStorageFromFile() {
        try (FileInputStream streamIn = new FileInputStream("src/Storage.dat")) {
            ObjectInputStream objectInputStream = new ObjectInputStream(streamIn);
            while (objectInputStream.readObject() != null) {
                System.out.println(objectInputStream.readObject());
            }
        } catch (EOFException ignored) {
            // ignored when the list is finished
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * checks if the weight is out of provided margin
     */
    public static boolean outOfMargin(double actualWeight, double targetWeight, double margin) {

        if (actualWeight <= 0 || targetWeight <= 0 || margin < 0)
            return true;

        if (actualWeight > targetWeight * (margin / 100.0 + 1)) {
            return true;
        } else if (actualWeight < targetWeight * (1 - margin / 100.0)) {
            return true;
        }
        return false;

    }

}
