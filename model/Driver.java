package model;

import java.io.Serializable;

public class Driver implements Serializable {

    private static final long serialVersionUID = 1432176189793004629L;
    private String            name;
    private String            phoneNumber;
    private String            email;

    // Link fields
    private Truck             truck;

    public Driver(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public String getEmail() {
        return email;
    }

    public Card createCard(String username, String password) {
        Card card = new Card(this, username, password);
        return card;
    }

    @Override
    public String toString() {
        return name;
    }
}
