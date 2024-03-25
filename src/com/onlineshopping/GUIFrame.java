package com.onlineshopping;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import static com.onlineshopping.Console.manager;

public class GUIFrame extends JFrame {

    //labels to display selected products in the table
    private ProductTableModel model;
    private JLabel productIdLabel = new JLabel();
    private JLabel productCategoryLabel = new JLabel();
    private JLabel productNameLabel = new JLabel();
    private JLabel productInfoLabel = new JLabel();
    private JLabel productAvailableLabel = new JLabel();
    private JTable productTable = new JTable();// Table to display available products

    private ShoppingCart shoppingCart;


    public GUIFrame() {

        List<Product> products = manager.getProductList();//Getting the list of products from shopping manager
        model = new ProductTableModel(products);// create table model


        JPanel topPanel = new JPanel(new BorderLayout());// Top Panle
        JButton shoppingCartBtn = new JButton("Shopping Cart");//Shopping cart button
        shoppingCartBtn.addActionListener(new MyActionListener());// Action listener for shopping cart button
        JLabel selctCategoryTxt = new JLabel("Select Product Category  ");

        String[] categoryTypes = {"All", "Clothing", "Electronics"};
        JComboBox categories = new JComboBox(categoryTypes); // Creating combo box
        categories.addItemListener(new ItemListener() {// Adding an item listener to combo box to detect the selected category
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {//If selected
                    String selectedCategory = (String) e.getItem();// Getting the selected category
                    filterTable(selectedCategory);// Calling filter table method based on selected category
                }
            }
        });


        // Nested panel for north-east button
        JPanel northEastPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        northEastPanel.add(shoppingCartBtn);
        northEastPanel.setBackground(new Color(207, 159, 255));
        // Nested panel for centered combo box
        JPanel topCenterPanel = new JPanel();
        topCenterPanel.add(selctCategoryTxt, BorderLayout.CENTER);
        topCenterPanel.add(categories, BorderLayout.CENTER);
        topCenterPanel.setBackground(new Color(207, 159, 255));
        // Adding nested panels to topPanel
        topPanel.add(northEastPanel, BorderLayout.NORTH);
        topPanel.add(topCenterPanel, BorderLayout.CENTER);


        JPanel centerPanel = new JPanel();// Creating center panel
        productTable.setModel(model);// set the table model
        productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {// Check if the selection is stable
                    int selectedRow = productTable.getSelectedRow();// Get the index of selected row
                    displaySelectedProduct(selectedRow);
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(productTable);// Adding table to a scroll pane
        centerPanel.add(scrollPane);
        centerPanel.setBackground(Color.BLACK);


        JPanel bottomPanel = new JPanel(new BorderLayout());


        JPanel bottomSouthPanel = new JPanel(new BorderLayout());
        JButton addToShoppingCartBtn = new JButton("Add to Shopping Cart");
        addToShoppingCartBtn.addActionListener(this::addToCartButton);
        bottomSouthPanel.add(addToShoppingCartBtn, BorderLayout.SOUTH);
        bottomSouthPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomSouthPanel.setBackground(new Color(170, 51, 106));


        JLabel selectedProduct = new JLabel("Selected Product - Details, ");
        JPanel bottomCenterPanel = new JPanel();
        bottomCenterPanel.setBackground(new Color(170, 51, 106));
        JPanel bottomWestPanel = new JPanel();
        bottomWestPanel.setBackground(new Color(170, 51, 106));

        bottomCenterPanel.add(selectedProduct);
        selectedProduct.setForeground(Color.WHITE);
        productIdLabel.setForeground(Color.WHITE);
        productNameLabel.setForeground(Color.WHITE);
        productCategoryLabel.setForeground(Color.WHITE);
        productInfoLabel.setForeground(Color.WHITE);
        productAvailableLabel.setForeground(Color.WHITE);
        bottomCenterPanel.add(productIdLabel);
        bottomCenterPanel.add(productCategoryLabel);
        bottomCenterPanel.add(productNameLabel);
        bottomCenterPanel.add(productInfoLabel);
        bottomCenterPanel.add(productAvailableLabel);
        bottomCenterPanel.setLayout(new BoxLayout(bottomCenterPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(bottomWestPanel, BorderLayout.WEST);
        bottomPanel.add(bottomCenterPanel, BorderLayout.CENTER);
        bottomPanel.add(bottomSouthPanel, BorderLayout.SOUTH);


        setLayout(new GridLayout(3, 1));
        add(topPanel);
        add(centerPanel);
        add(bottomPanel);
    }

    private void filterTable(String selectedCategory) {
        List<Product> filteredProducts = new ArrayList<>(); // creating a list to store products in selected category
        if (selectedCategory.equals("All")) {
            filteredProducts = manager.getProductList(); // Get all products from manager
        } else {
            for (Product product : manager.getProductList()) {
                if (product instanceof Electronics && selectedCategory.equals("Electronics")) {
                    filteredProducts.add(product);// Add to list if Electronics category selected
                } else if (product instanceof Clothing && selectedCategory.equals("Clothing")) {
                    filteredProducts.add(product);// Add to list if Clothing category selected
                }
            }

        }
        model.setProducts(filteredProducts);// Update the table with filtered products
        model.fireTableDataChanged();// Notify the table that product data has changed to trigger a refresh


    }

    class ProductTableModel extends AbstractTableModel {// Creating ProductTableModel class to customize table model
        private String[] columnNames = {"Product ID", "Product Name", "Price(£)", "Category", "Info"};// Column names
        private List<Product> products;// list of products

        public ProductTableModel(List<Product> products) {
            this.products = products;
        }//Constructor

        public void setProducts(List<Product> products) {// Method to update the products in table model
            this.products = products;
            fireTableDataChanged();//Notify the table to refresh
        }


        @Override
        public int getRowCount() {
            return products.size();
        }// return number of rows

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }// return number of columns

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }// return name of the column


        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Product product = products.get(rowIndex);// get the products
            switch (columnIndex) {
                case 0:
                    return product.getProductID();
                case 1:
                    return product.getProductName();
                case 2:
                    return product.getPrice();
                case 3:
                    return product instanceof Electronics ? "Electronic" : "Clothing";// Display category based on product type
                case 4:
                    if (product instanceof Clothing) {
                        Clothing clothing = (Clothing) product;
                        return clothing.getSize() + ", " + clothing.getColour();// Display if type is clothing
                    } else if (product instanceof Electronics) {
                        Electronics electronics = (Electronics) product;
                        return electronics.getBrand() + ", " + electronics.getWarrantyPeriod();// Display if type is electronics
                    }
                default:
                    return null;


            }
        }

    }

    private void displaySelectedProduct(int selectedRow) {
        if (selectedRow >= 0) {// If a row is selected
            Product product = model.products.get(selectedRow);// Get the selected product from the table

            //Update labels with product details
            productIdLabel.setText("Product ID: " + product.getProductID());
            productNameLabel.setText("Product Name: " + product.getProductName());
            productCategoryLabel.setText(product instanceof Electronics ? "Product Category: Electronic" : "Product Category: Clothing");

            if (product instanceof Clothing) {
                Clothing clothing = (Clothing) product;
                productInfoLabel.setText("Product Info: Size - " + clothing.getSize() + ", Colour - " + clothing.getColour());
            } else if (product instanceof Electronics) {
                Electronics electronics = (Electronics) product;
                productInfoLabel.setText("Product Info: Brand - " + electronics.getBrand() + ", Warranty Period: " + electronics.getWarrantyPeriod() + " Months");
            }

            productAvailableLabel.setText("Items Available: " + String.valueOf(product.getNoOfAvailableItems()));


        } else {

            //If product is not selected
            productIdLabel.setText("");
            productNameLabel.setText("");
            productCategoryLabel.setText("");
            productInfoLabel.setText("");
            productAvailableLabel.setText("");

        }
    }


    private void addToCartButton(ActionEvent evt) {
        int selectedRow = productTable.getSelectedRow();// Get the index of selected product
        if (selectedRow != -1) {// if selected
            Product product = model.products.get(selectedRow); // Retrieve product from table model

            if (shoppingCart == null) {
                shoppingCart = new ShoppingCart();// create a shopping cart if not created
            }
            shoppingCart.addToCart(product);// add the selected product to the shopping cart

            //shoppingCart.getListOfProducts();
            ShoppingCartGUI.getInstance().setShoppingCart(shoppingCart);
            refreshCartTable();// refresh the cart table



        } else {// If no products are created
            JOptionPane.showMessageDialog(this, "Please select a product to add to cart.");
        }
    }

    private void refreshCartTable(){
        // call populate cart table to update the cart table
        ShoppingCartGUI.getInstance().populateCartTable();
    }


}










