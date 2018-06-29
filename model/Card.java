package model;

import java.io.Serializable;

public class Card implements Serializable {

    private static final long serialVersionUID = -7013923319001924204L;
    private String            username;
    private String            password;

    // Link fields
    private Driver            driver;

    public Card(Driver driver, String username, String password) {
        this.driver = driver;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Driver getDriver() {
        return driver;
    }

    @Override
    public String toString() {
        return username;
    }
}
