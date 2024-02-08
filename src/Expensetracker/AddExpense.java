package Expensetracker;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class AddExpense extends JFrame {
    private JTextField dayField, dateField, descriptionField, categoryField, amountField;
    private JButton addButton;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel totalAmountLabel;

    class ImagePanel extends JPanel {
        private Image backgroundImage;

        public ImagePanel() {
            try {
                backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("bg.jpg")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public AddExpense() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Expense Tracker");

        JLabel j1 = createLabel("Day: ", 50, 130, 200, 40);
        dayField = createTextField(320, 130, 150, 40);
        j1.setForeground(new Color(91, 8, 136));
        j1.setHorizontalAlignment(JLabel.RIGHT);
        j1.setFont(new Font("SansSerif", Font.BOLD, 30));

        JLabel j2 = createLabel("Date: ", 50, 210, 200, 40);
        dateField = createTextField(320, 210, 150, 40);
        j2.setForeground(new Color(91, 8, 136));
        j2.setHorizontalAlignment(JLabel.RIGHT);
        j2.setFont(new Font("SansSerif", Font.BOLD, 30));

        JLabel j3 = createLabel("Description: ", 50, 290, 200, 40);
        descriptionField = createTextField(320, 290, 150, 40);
        j3.setForeground(new Color(91, 8, 136));
        j3.setHorizontalAlignment(JLabel.RIGHT);
        j3.setFont(new Font("SansSerif", Font.BOLD, 30));

        JLabel j4 = createLabel("Category: ", 50, 370, 200, 40);
        categoryField = createTextField(320, 370, 150, 40);
        j4.setForeground(new Color(91, 8, 136));
        j4.setHorizontalAlignment(JLabel.RIGHT);
        j4.setFont(new Font("SansSerif", Font.BOLD, 30));

        JLabel j5 = createLabel("Amount: ", 50, 450, 200, 40);
        amountField = createTextField(320, 450, 150, 40);
        j5.setForeground(new Color(91, 8, 136));
        j5.setHorizontalAlignment(JLabel.RIGHT);
        j5.setFont(new Font("SansSerif", Font.BOLD, 30));

        addButton = new JButton("Add Expense");
        addButton.setBounds(200, 580, 150, 30);
        addButton.setBackground(Color.pink);
        addButton.addActionListener(e -> {
            try {
                addExpense();
            } catch (SQLException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        JLabel tableLabel = createLabel("Expense Details", 650, 100, 400, 50);
        tableLabel.setFont(new Font("SansSerif", Font.BOLD, 35));
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Day");
        tableModel.addColumn("Date");
        tableModel.addColumn("Description");
        tableModel.addColumn("Category");
        tableModel.addColumn("Amount");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(650, 200, 700, 300);
        scrollPane.setBackground(Color.WHITE);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(1200, 580, 150, 30);
        exitButton.setBackground(Color.pink);
        exitButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                dispose();
            }
        });

        ImagePanel imagePanel = new ImagePanel();
        setContentPane(imagePanel);

        totalAmountLabel = createLabel("Total Amount: ₹0.00", 650, 520, 400, 30);
        totalAmountLabel.setFont(new Font("SansSerif", Font.BOLD, 20));


        // Add components to the frame
        add(j1);
        add(dayField);
        add(j2);
        add(dateField);
        add(j3);
        add(descriptionField);
        add(j4);
        add(categoryField);
        add(j5);
        add(amountField);
        add(addButton);
        add(tableLabel);
        add(scrollPane);
        add(totalAmountLabel);
        add(exitButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private JLabel createLabel(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setFont(new Font("Comic Sans", Font.ITALIC, 20));
        return label;
    }

    private JTextField createTextField(int x, int y, int width, int height) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, width, height);
        return textField;
    }

    public void addExpense() throws SQLException, ClassNotFoundException {
        String day = dayField.getText();
        String date = dateField.getText();
        String description = descriptionField.getText();
        String category = categoryField.getText();
        String amount = amountField.getText();

        double totalAmount = calculateTotalAmount(amount);

        tableModel.addRow(new Object[]{day, date, description, category, amount});
        totalAmountLabel.setText("Total Amount: ₹" + String.format("%.2f", totalAmount));

        insertIntoDatabase(day, date, description, category, amount);

        dayField.setText("");
        dateField.setText("");
        descriptionField.setText("");
        categoryField.setText("");
        amountField.setText("");
    }

    private void insertIntoDatabase(String day, String date, String description, String category, String amount) throws ClassNotFoundException, SQLException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/expensetracker?autoReconnect=true&useSSL=false", "root", "");
        String query = "INSERT INTO addexpense (day, date, descrip, category, amount) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, day);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, description);
            preparedStatement.setString(4, category);
            preparedStatement.setString(5, amount);
            preparedStatement.executeUpdate();
        }
        finally {
            con.close();
        }
    }

    private double calculateTotalAmount(String newAmount) {
        double currentTotal = 0.0;
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            Object value = tableModel.getValueAt(row, 4);
            if (value instanceof String) {
                try {
                    double rowAmount = Double.parseDouble((String) value);
                    currentTotal += rowAmount;
                } catch (NumberFormatException ignored) {
                }
            }
        }
        try {
            double amount = Double.parseDouble(newAmount);
            currentTotal += amount;
        } catch (NumberFormatException ignored) {
        }
        return currentTotal;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(AddExpense::new);
    }
}