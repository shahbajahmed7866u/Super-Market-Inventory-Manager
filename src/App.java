import service.Store;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Store store = new Store();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Supermarket Menu ---");
            System.out.println("1. Add Item");
            System.out.println("2. Delete Item");
            System.out.println("3. Update Quantity");
            System.out.println("4. Update Price");
            System.out.println("5. View Inventory");
            System.out.println("6. Total Store Worth");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Quantity: ");
                    int qty = scanner.nextInt();
                    store.addItem(name, price, qty);
                }
                case 2 -> {
                    System.out.print("Enter Item Name to delete: ");
                    String name = scanner.nextLine();
                    store.deleteItem(name);
                }
                case 3 -> {
                    System.out.print("Enter Item Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Quantity change (+ for add, - for sell): ");
                    int change = scanner.nextInt();
                    store.updateQuantity(name, change);
                }
                case 4 -> {
                    System.out.print("Enter Item Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter new price: ");
                    double newPrice = scanner.nextDouble();
                    store.updatePrice(name, newPrice);
                }
                case 5 -> store.viewInventory();
                case 6 -> store.totalStoreWorth();
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 0);

        scanner.close();
    }
}
