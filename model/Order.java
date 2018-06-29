package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {

    private static final long serialVersionUID = 6617985724254451386L;
    private String            id;
    private double            weight;

    // Link fields
    private ProductType       typeOfProduct;
    private List<SubOrder>    subOrders        = new ArrayList<>();

    public Order(String id, ProductType typeOfProduct, double weight) {
        this.id = id;
        this.typeOfProduct = typeOfProduct;
        this.weight = weight;
    }

    public ProductType getTypeOfProduct() {
        return typeOfProduct;
    }

    public void setTypeOfProduct(ProductType typeOfProduct) {
        this.typeOfProduct = typeOfProduct;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public List<SubOrder> getSubOrders() {
        return new ArrayList<>(subOrders);
    }

    public SubOrder createSubOrder(Trailer trailer, double weight, double weightMargin, LocalDate loadingDate,
            int loadingTime) {
        SubOrder subOrder = new SubOrder(trailer, weight, weightMargin, loadingDate, loadingTime);
        subOrder.setOrder(this);
        subOrders.add(subOrder);
        return subOrder;
    }

    public void deleteSubOrder(SubOrder subOrder) {
        subOrder.setOrder(null);
        subOrders.remove(subOrder);
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Order ID " + id;
    }
}
