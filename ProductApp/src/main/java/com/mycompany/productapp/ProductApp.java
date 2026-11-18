/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.productapp;

/**
 *
 * @author moele
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ProductApp {

    private JTextField txtCustomerName;
    private JComboBox<String> cbProductName;
    private JTextField txtPrice;
    private JTextArea displayArea;
    private DecimalFormat currencyFormat = new DecimalFormat("#0.00");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ProductApp().createAndShowGUI();
            }
        });
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Product App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 700);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 15));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        inputPanel.add(new JLabel("Customer Name:"));
        txtCustomerName = new JTextField();
        inputPanel.add(txtCustomerName);

        inputPanel.add(new JLabel("Product Name:"));
        String[] products = {
            "Cozy Nights Monarch", 
            "Restonic Barbados", 
            "Sealy Phuket"
        };
        cbProductName = new JComboBox<>(products);
        inputPanel.add(cbProductName);

        inputPanel.add(new JLabel("Base Price (R):"));
        txtPrice = new JTextField();
        inputPanel.add(txtPrice);

        frame.add(inputPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        Color lightBlue = new Color(173, 216, 230);

        JButton btnProcessSale = new JButton("Process Sale & Calculate");
        btnProcessSale.setFont(new Font("Arial", Font.BOLD, 14));
        btnProcessSale.setBackground(lightBlue);
        btnProcessSale.setForeground(Color.BLACK);
        btnProcessSale.setFocusPainted(false);
        btnProcessSale.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnProcessSale.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnClear = new JButton("Clear Fields");
        btnClear.setFont(new Font("Arial", Font.BOLD, 14));
        btnClear.setBackground(lightBlue);
        btnClear.setForeground(Color.BLACK);
        btnClear.setFocusPainted(false);
        btnClear.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnClear.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnExit = new JButton("X - Exit Application");
        btnExit.setFont(new Font("Arial", Font.BOLD, 14));
        btnExit.setBackground(lightBlue);
        btnExit.setForeground(Color.BLACK);
        btnExit.setFocusPainted(false);
        btnExit.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnExit.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(btnProcessSale);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(btnClear);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(btnExit);

        frame.add(buttonPanel, BorderLayout.CENTER);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        displayArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Processed Sales Records"));
        scrollPane.setPreferredSize(new Dimension(500, 300));
        
        frame.add(scrollPane, BorderLayout.SOUTH);

        btnProcessSale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processSale(frame);
            }
        });

        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtCustomerName.setText("");
                txtPrice.setText("");
                cbProductName.setSelectedIndex(0);
                displayArea.setText("");
                txtCustomerName.requestFocus();
            }
        });

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(frame, 
                        "Are you sure you want to exit?", 
                        "Exit Confirmation", 
                        JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        frame.setVisible(true);
    }

    private void processSale(JFrame frame) {
        String customerName = txtCustomerName.getText().trim();
        String productName = (String) cbProductName.getSelectedItem();
        String priceText = txtPrice.getText().trim();

        if (customerName.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            if (price < 0) {
                JOptionPane.showMessageDialog(frame, "Price cannot be negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double vatAmount = price * 0.15;
            double commissionAmount = price * 0.05;
            double totalSalesAmount = price + vatAmount + commissionAmount;

            displayArea.append("====================================================\n");
            displayArea.append(String.format("Customer: %s%n", customerName));
            displayArea.append(String.format("Product:  %s%n", productName));
            displayArea.append("----------------------------------------------------\n");
            displayArea.append(String.format("Base Price:        R %s%n", currencyFormat.format(price)));
            displayArea.append(String.format("VAT (15%%):         R %s%n", currencyFormat.format(vatAmount)));
            displayArea.append(String.format("Commission (5%%):   R %s%n", currencyFormat.format(commissionAmount)));
            displayArea.append(String.format("TOTAL SALES:       R %s%n", currencyFormat.format(totalSalesAmount)));
            displayArea.append("====================================================\n\n");

            saveSaleToFile(customerName, productName, price, vatAmount, commissionAmount, totalSalesAmount);

            txtCustomerName.setText("");
            txtPrice.setText("");
            cbProductName.setSelectedIndex(0);
            txtCustomerName.requestFocus();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid number for the price.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveSaleToFile(String customer, String product, double price, double vat, double commission, double total) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt", true))) {
            writer.write("====================================================\n");
            writer.write(String.format("Customer: %s%n", customer));
            writer.write(String.format("Product:  %s%n", product));
            writer.write("----------------------------------------------------\n");
            writer.write(String.format("Base Price:        R %s%n", currencyFormat.format(price)));
            writer.write(String.format("VAT (15%%):         R %s%n", currencyFormat.format(vat)));
            writer.write(String.format("Commission (5%%):   R %s%n", currencyFormat.format(commission)));
            writer.write(String.format("TOTAL SALES:       R %s%n", currencyFormat.format(total)));
            writer.write("====================================================\n\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving to file: " + e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}