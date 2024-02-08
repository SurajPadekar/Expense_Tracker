package Expensetracker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class Features extends JFrame {

    static class ImagePanel extends JPanel {
        private Image backgroundImage;

        public ImagePanel() {
            try {
                backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("photo.jpg")));
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

    public Features() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Features");

        JLabel j1 = new JLabel("FEATURES:");
        j1.setBounds(800, 70, 400, 60);
        j1.setHorizontalAlignment(JLabel.LEFT);
        j1.setFont(new Font("SansSerif", Font.BOLD, 50));
        j1.setForeground(new Color(91, 8, 136));

        JPanel p1 = new JPanel();
        p1.setBounds(800,140,400,150);
        p1.setBackground(new Color(91, 8, 136));

        JLabel j2 = new JLabel("Expense Tracking");
        j2.setBounds(800, 150, 400, 40);
        j2.setHorizontalAlignment(JLabel.LEFT);
        j2.setFont(new Font("SansSerif", Font.BOLD, 30));
        j2.setForeground(new Color(229, 207, 247));

        JLabel j3 = new JLabel("Effortlessly record and categorize");
        j3.setBounds(700, 260, 400, 40);
        j3.setHorizontalAlignment(JLabel.LEFT);
        j3.setFont(new Font("SansSerif", Font.BOLD, 20));
        j3.setForeground(new Color(229, 207, 247));

        JLabel j4 = new JLabel("your expenses to stay organized.");
        j4.setBounds(730, 300, 400, 40);
        j4.setHorizontalAlignment(JLabel.LEFT);
        j4.setFont(new Font("SansSerif", Font.BOLD, 20));
        j4.setForeground(new Color(229, 207, 247));

        JPanel p2 = new JPanel();
        p2.setBounds(800,350,400,150);
        p2.setBackground(new Color(91, 8, 136));

        JLabel j5 = new JLabel("Budget Management");
        j5.setBounds(800, 550, 400, 60);
        j5.setHorizontalAlignment(JLabel.LEFT);
        j5.setFont(new Font("SansSerif", Font.BOLD, 30));
        j5.setForeground(new Color(229, 207, 247));

        JLabel j6 = new JLabel("Create customized budgets and monitor");
        j6.setBounds(700, 600, 400, 60);
        j6.setHorizontalAlignment(JLabel.LEFT);
        j6.setFont(new Font("SansSerif", Font.BOLD, 20));
        j6.setForeground(new Color(229, 207, 247));

        JLabel j7 = new JLabel("your progress towards financial goals.");
        j7.setBounds(700, 630, 400, 60);
        j7.setHorizontalAlignment(JLabel.LEFT);
        j7.setFont(new Font("SansSerif", Font.BOLD, 20));
        j7.setForeground(new Color(229, 207, 247));

        JButton getStartedButton = new JButton("Get Started");
        getStartedButton.setBounds(1000, 663, 150, 40);
        getStartedButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        getStartedButton.setForeground(new Color(91, 8, 136));
        getStartedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                openAddExpensePage();
            }
        });

        ImagePanel imagePanel = new ImagePanel();
        setContentPane(imagePanel);

        add(j1);
        add(p1);
        p1.add(j2);
        p1.add(j3);
        p1.add(j4);
        add(p2);
        p2.add(j5);
        p2.add(j6);
        p2.add(j7);
        add(getStartedButton);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void openAddExpensePage() {
        AddExpense addExpensePage = new AddExpense();
        this.dispose();
        addExpensePage.setVisible(true);
    }

    public static void main(String[] args) {
        new Features();
    }
}