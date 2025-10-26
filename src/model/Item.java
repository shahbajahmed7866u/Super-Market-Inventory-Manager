package model;

public class Item {
    private String name;
    private double price;
    private int quantity;

    public Item(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public void setPrice(double price) { this.price = price; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getTotalWorth() {
        return price * quantity;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Price: " + price +
               ", Quantity: " + quantity + ", Total: " + getTotalWorth();
    }
}
