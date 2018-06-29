package test;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Test;

import model.Card;
import model.Dock;
import model.Driver;
import model.Order;
import model.ProductType;
import model.Trailer;
import model.Truck;
import service.Service;
import storage.Storage;

public class ServiceTest {

    @Test
    public void testOrganizeSchedules() {
        Driver[] driver = new Driver[3];
        Card[] card = new Card[3];
        Truck[] truck = new Truck[3];
        Trailer[] trailer = new Trailer[3];
        for (int i = 0; i < 3; i++) {
            driver[i] = Service.createDriver("Driver " + i, "1232334" + i, "driver" + i + "@dc.com");
            card[i] = Service.createCard(driver[i], "driver " + i, "123456");
            truck[i] = Service.createTruck("10" + 1);
            trailer[i] = Service.createTrailer("" + i, 150);
            Service.assignDriver(driver[i], truck[i], trailer[i]);
        }

        Order[] orders = new Order[3];
        for (int i = 0; i < 3; i++) {

            orders[i] = Service.createOrder("" + i, ProductType.values()[i], 1000);
            orders[i].createSubOrder(trailer[i], 500, 10, LocalDate.now(), (i + 1) * 10);

            orders[i].createSubOrder(trailer[2 - i], 500, 10, LocalDate.now(), (i + 1) * 10);
        }
        Dock dock1 = Service.createDock("1", ProductType.Boxed);
        Dock dock2 = Service.createDock("2", ProductType.Palletised);
        Dock dock3 = Service.createDock("3", ProductType.ChrismasTree);

        Service.organizeSchedules();

        Assert.assertEquals(6, Storage.getSchedules().size());
        Assert.assertEquals(dock3, Storage.getSchedules().get(0).getDock());
        Assert.assertEquals(dock3, Storage.getSchedules().get(1).getDock());
        Assert.assertEquals(dock1, Storage.getSchedules().get(2).getDock());
        Assert.assertEquals(dock1, Storage.getSchedules().get(3).getDock());
        Assert.assertEquals(dock2, Storage.getSchedules().get(4).getDock());
        Assert.assertEquals(dock2, Storage.getSchedules().get(5).getDock());
        Assert.assertTrue(LocalTime.of(8, 00).equals(Storage.getSchedules().get(0).getSubOrder().getLoadingTime()));
        Assert.assertTrue(LocalTime.of(10, 10).equals(Storage.getSchedules().get(1).getSubOrder().getLoadingTime()));
        Assert.assertTrue(LocalTime.of(8, 00).equals(Storage.getSchedules().get(2).getSubOrder().getLoadingTime()));
        Assert.assertTrue(LocalTime.of(10, 20).equals(Storage.getSchedules().get(3).getSubOrder().getLoadingTime()));
        Assert.assertTrue(LocalTime.of(8, 00).equals(Storage.getSchedules().get(4).getSubOrder().getLoadingTime()));
        Assert.assertTrue(LocalTime.of(10, 30).equals(Storage.getSchedules().get(5).getSubOrder().getLoadingTime()));
        Assert.assertTrue(LocalTime.of(8, 10).equals(Storage.getSchedules().get(0).getSubOrder().getLoadingEndTime()));
        Assert.assertTrue(LocalTime.of(10, 20).equals(Storage.getSchedules().get(1).getSubOrder().getLoadingEndTime()));
        Assert.assertTrue(LocalTime.of(8, 20).equals(Storage.getSchedules().get(2).getSubOrder().getLoadingEndTime()));
        Assert.assertTrue(LocalTime.of(10, 40).equals(Storage.getSchedules().get(3).getSubOrder().getLoadingEndTime()));
        Assert.assertTrue(LocalTime.of(8, 30).equals(Storage.getSchedules().get(4).getSubOrder().getLoadingEndTime()));
        Assert.assertTrue(LocalTime.of(11, 00).equals(Storage.getSchedules().get(5).getSubOrder().getLoadingEndTime()));
    }

    @Test
    public void testVerifyUser() {
        Driver driver = Service.createDriver("Benn", "22336699", "benn@benndc.dk");
        Driver driver1 = Service.createDriver("Mikael", "99336699", "miakel@mikaeldc.dk");

        Card cardBenn = Service.createCard(driver, "benn", "123456");
        Card cardMikael = Service.createCard(driver1, "mikael", "123456");

        Assert.assertEquals(cardBenn, Service.verifyUser("benn", "123456"));
        Assert.assertEquals(cardMikael, Service.verifyUser("mikael", "123456"));
        Assert.assertNull(Service.verifyUser("benn", "1241"));
        Assert.assertNull(Service.verifyUser("mikel", "123456"));
        Assert.assertNull(Service.verifyUser("Benn", "123456"));
        Assert.assertNull(Service.verifyUser(null, "123456"));
        Assert.assertNull(Service.verifyUser("benn", null));
    }

    @Test
    public void testOutOfMargin() {
        Assert.assertFalse(Service.outOfMargin(100, 100, 10));
        Assert.assertFalse(Service.outOfMargin(110, 100, 10));
        Assert.assertFalse(Service.outOfMargin(90, 100, 10));
        Assert.assertFalse(Service.outOfMargin(100, 100, 0));
        Assert.assertTrue(Service.outOfMargin(50, 100, 10));
        Assert.assertTrue(Service.outOfMargin(150, 100, 10));
        Assert.assertTrue(Service.outOfMargin(0, 100, 10));
        Assert.assertTrue(Service.outOfMargin(-100, 100, 10));
        Assert.assertTrue(Service.outOfMargin(-100, -100, 10));
    }

}
