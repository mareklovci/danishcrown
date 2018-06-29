package model;

import java.io.Serializable;

public class Truck implements Serializable {

    private static final long serialVersionUID = 8391654626870656755L;
    private String            number;

    // Link fields
    private Driver            driver;
    private Trailer           trailer;

    public Truck(String number) {
        this.number = number;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Trailer getTrailer() {
        return trailer;
    }

    public void setTrailer(Trailer trailer) {
        this.trailer = trailer;
    }

    public String getNumber() {
        return number;
    }

}
