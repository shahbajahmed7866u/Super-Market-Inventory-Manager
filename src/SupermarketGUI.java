

import model.Item;
import service.Store;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class SupermarketGUI {

    private Store store;
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField nameField, priceField, qtyField;
    private JLabel totalWorthLabel, itemCountLabel;

    public SupermarketGUI() {
        store = new Store();
        setupLookAndFeel();
        initialize();
        refreshTable();
    }

    private void setupLookAndFeel() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}
    }

    private void initialize() {
        frame = new JFrame("Supermarket Inventory Management");
        frame.setSize(900, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        // -------- Table Panel --------
        tableModel = new DefaultTableModel(new String[]{"Name", "Price", "Quantity", "Total"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setPreferredSize(new Dimension(550, 400));
        frame.add(tableScroll, BorderLayout.CENTER);

        // Row selection fills the form
        table.getSelectionModel().addListSelectionListener(e -> fillFormFromSelectedRow());

        // -------- Form Panel --------
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(300, 400));
        formPanel.setBorder(BorderFactory.createTitledBorder("Item Actions"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(15);
        formPanel.add(nameField, gbc);

        // Price
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Price:"), gbc);
        gbc.gridx = 1;
        priceField = new JTextField(15);
        formPanel.add(priceField, gbc);

        // Quantity
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        qtyField = new JTextField(15);
        formPanel.add(qtyField, gbc);

        // Buttons
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new GridLayout(3,2,5,5));
        JButton addBtn = new JButton("Add Item");
        JButton deleteBtn = new JButton("Delete Item");
        JButton updatePriceBtn = new JButton("Update Price");
        JButton updateQtyBtn = new JButton("Update Qty");
        JButton refreshBtn = new JButton("Refresh Table");

        btnPanel.add(addBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(updatePriceBtn);
        btnPanel.add(updateQtyBtn);
        btnPanel.add(refreshBtn);

        formPanel.add(btnPanel, gbc);

        // Statistics Panel (always visible)
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JPanel statsPanel = new JPanel(new GridLayout(2,1));
        totalWorthLabel = new JLabel("Total Store Worth: 0");
        itemCountLabel = new JLabel("Total Items: 0");
        statsPanel.add(totalWorthLabel);
        statsPanel.add(itemCountLabel);
        formPanel.add(statsPanel, gbc);

        frame.add(formPanel, BorderLayout.EAST);

        // -------- Button Actions --------
        addBtn.addActionListener(e -> addItem());
        deleteBtn.addActionListener(e -> deleteItem());
        updatePriceBtn.addActionListener(e -> updatePrice());
        updateQtyBtn.addActionListener(e -> updateQuantity());
        refreshBtn.addActionListener(e -> refreshTable());

        frame.setVisible(true);
    }

    // -------- Table → Form --------
    private void fillFormFromSelectedRow() {
        int row = table.getSelectedRow();
        if(row >= 0) {
            nameField.setText(table.getValueAt(row,0).toString());
            priceField.setText(table.getValueAt(row,1).toString());
            qtyField.setText(table.getValueAt(row,2).toString());
        }
    }

    // -------- GUI Actions --------
    private void addItem() {
        String name = nameField.getText().trim();
        if(name.isEmpty()) { showError("Name required!"); return; }

        try {
            double price = Double.parseDouble(priceField.getText().trim());
            int qty = Integer.parseInt(qtyField.getText().trim());
            if(price < 0 || qty < 0) { showError("Price and quantity cannot be negative!"); return; }

            store.addItem(name, price, qty);
            refreshTable();
        } catch(Exception e) { showError("Invalid price or quantity!"); }
    }

    private void deleteItem() {
        String name = nameField.getText().trim();
        if(name.isEmpty()) { showError("Select an item to delete!"); return; }
        int confirm = JOptionPane.showConfirmDialog(frame, "Delete item "+name+"?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION) {
            store.deleteItem(name);
            refreshTable();
        }
    }

    private void updatePrice() {
        String name = nameField.getText().trim();
        if(name.isEmpty()) { showError("Select an item!"); return; }
        try {
            double price = Double.parseDouble(priceField.getText().trim());
            if(price < 0) { showError("Price cannot be negative!"); return; }
            store.updatePrice(name, price);
            refreshTable();
        } catch(Exception e) { showError("Invalid price!"); }
    }

    private void updateQuantity() {
        String name = nameField.getText().trim();
        if(name.isEmpty()) { showError("Select an item!"); return; }
        try {
            int qty = Integer.parseInt(qtyField.getText().trim());
            store.updateQuantity(name, qty);
            refreshTable();
        } catch(Exception e) { showError("Invalid quantity!"); }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        try {
            java.lang.reflect.Field itemsField = Store.class.getDeclaredField("items");
            itemsField.setAccessible(true);
            ArrayList<Item> items = (ArrayList<Item>) itemsField.get(store);

            double total = 0;
            for(Item item: items) {
                tableModel.addRow(new Object[]{
                        item.getName(),
                        item.getPrice(),
                        item.getQuantity(),
                        item.getTotalWorth()
                });
                total += item.getTotalWorth();
            }

            // Update statistics automatically
            totalWorthLabel.setText("Total Store Worth: " + total);
            itemCountLabel.setText("Total Items: " + items.size());

        } catch(Exception e) { showError("Cannot load items: "+e.getMessage()); }
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(frame, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SupermarketGUI::new);
    }
}
