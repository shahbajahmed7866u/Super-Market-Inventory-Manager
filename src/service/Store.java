package service;

import model.Item;
import java.io.*;
import java.util.ArrayList;

public class Store {
    private ArrayList<Item> items = new ArrayList<>();
    private final String FILE_NAME = "src/database/inventory.txt";

    public Store() {
        loadFromFile();
    }

    // Add new item (name must be unique)
    public void addItem(String name, double price, int quantity) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                System.out.println("Item already exists!");
                return;
            }
        }
        items.add(new Item(name, price, quantity));
        saveToFile();
        System.out.println(name + " added successfully!");
    }

    // Delete item by name
    public void deleteItem(String name) {
        boolean removed = items.removeIf(item -> item.getName().equalsIgnoreCase(name));
        if (removed) {
            saveToFile();
            System.out.println(name + " removed successfully!");
        } else {
            System.out.println("Item not found!");
        }
    }

    // Update quantity (+ for add, - for sell)
    public void updateQuantity(String name, int quantityChange) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                item.setQuantity(item.getQuantity() + quantityChange);
                saveToFile();
                System.out.println("Quantity updated!");
                return;
            }
        }
        System.out.println("Item not found!");
    }

    // Update price
    public void updatePrice(String name, double newPrice) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                item.setPrice(newPrice);
                saveToFile();
                System.out.println("Price updated!");
                return;
            }
        }
        System.out.println("Item not found!");
    }

    // View all items
    public void viewInventory() {
        System.out.println("------ Inventory ------");
        if(items.isEmpty()) {
            System.out.println("No items available.");
            return;
        }
        for (Item item : items) {
            System.out.println(item);
        }
    }

    // Total store worth
    public void totalStoreWorth() {
        double total = 0;
        for (Item item : items) {
            total += item.getTotalWorth();
        }
        System.out.println("Total store worth: " + total);
    }

    // Save items to file
    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Item item : items) {
                writer.write(item.getName() + "," + item.getPrice() + "," + item.getQuantity());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    // Load items from file
    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                double price = Double.parseDouble(parts[1]);
                int quantity = Integer.parseInt(parts[2]);
                items.add(new Item(name, price, quantity));
            }
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }
}
