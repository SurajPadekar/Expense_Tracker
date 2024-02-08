package Expensetracker;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.awt.event.ActionListener;
import java.awt.event.*;

class ImagePanel extends JPanel {
    private Image backgroundImage;

    public ImagePanel() {
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("Home1.jpg")));
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

public class HomePage extends JFrame {
    JTextField txtUser;
    JPasswordField txtPass;

    HomePage() {
        setTitle("Login page");
        setSize(600, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JLabel j1 = new JLabel("TRACK");
        j1.setBounds(100, 70, 300, 70);
        j1.setHorizontalAlignment(JLabel.LEFT);
        j1.setFont(new Font("SansSerif", Font.BOLD, 73));
        j1.setForeground(new Color(91, 8, 136));

        JLabel j2 = new JLabel("YOUR");
        j2.setBounds(100,170,300,70);
        j2.setHorizontalAlignment(JLabel.LEFT);
        j2.setFont(new Font("SansSerif", Font.BOLD, 73));
        j2.setForeground(new Color(91, 8, 136));

        JLabel j3 = new JLabel("EXPENSES");
        j3.setBounds(100,270,400,70);
        j3.setHorizontalAlignment(JLabel.LEFT);
        j3.setFont(new Font("SansSerif", Font.BOLD, 73));
        j3.setForeground(new Color(91, 8, 136));

        JLabel j4 = new JLabel("AND SAVE");
        j4.setBounds(100,370,400,70);
        j4.setHorizontalAlignment(JLabel.LEFT);
        j4.setFont(new Font("SansSerif", Font.BOLD, 73));
        j4.setForeground(new Color(91, 8, 136));

        JLabel j5 = new JLabel("MONEY!");
        j5.setBounds(100,470,400,70);
        j5.setHorizontalAlignment(JLabel.LEFT);
        j5.setFont(new Font("SansSerif", Font.BOLD, 73));
        j5.setForeground(new Color(91, 8, 136));

        JLabel j6 = new JLabel("MANAGE YOUR FINANCES LIKE A PRO WITH OUR");
        j6.setBounds(150,640,400,25);
        j6.setFont(new Font("SansSerif", Font.BOLD, 17));
        j6.setForeground(new Color(91, 8, 136));

        JLabel j7 = new JLabel("EASY-TO-USE EXPENSE TRACKER.");
        j7.setBounds(190,665,400,25);
        j7.setFont(new Font("SansSerif", Font.BOLD, 17));
        j7.setForeground(new Color(91, 8, 136));

        JLabel j8 = new JLabel("Username:");
        j8.setBounds(800, 220, 200, 50);
        j8.setHorizontalAlignment(JLabel.RIGHT);
        j8.setFont(new Font("SansSerif", Font.BOLD, 20));
        j8.setBackground(new Color(91, 8, 136));

        txtUser = new JTextField();
        txtUser.setBounds(1030, 220, 240, 40);

        JLabel j9 = new JLabel("Password:");
        j9.setBounds(800, 290, 200, 50);
        j9.setHorizontalAlignment(JLabel.RIGHT);
        j9.setFont(new Font("SansSerif", Font.BOLD, 20));
        j9.setBackground(new Color(91, 8, 136));

        txtPass = new JPasswordField();
        txtPass.setBounds(1030, 290, 240, 40);

        JButton btnlogin = new JButton("Login");
        btnlogin.setBounds(1000, 380, 150, 40);
        btnlogin.setBackground(new java.awt.Color(49, 126, 242));
        btnlogin.setFont(new java.awt.Font("Thoma", 0, 18));
        btnlogin.addActionListener(this::btnloginActionPerformed);

        ImagePanel imagePanel = new ImagePanel();
        setContentPane(imagePanel);
        setLayout(null);

        add(j1);
        add(j2);
        add(j3);
        add(j4);
        add(j5);
        add(j6);
        add(j7);
        add(j8);
        add(j9);
        add(txtUser);
        add(txtPass);
        add(btnlogin);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void btnloginActionPerformed(java.awt.event.ActionEvent evt) {
        if (txtUser.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username");
            txtUser.setText("");
            txtUser.grabFocus();
        } else if (txtPass.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Please enter Password");
            txtPass.setText("");
            txtPass.grabFocus();
        } else {
            String uname = txtUser.getText();
            char[] passChars = txtPass.getPassword();
            String pass = new String(passChars);

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/expensetracker?autoReconnect=true&useSSL=false", "root", "");
                PreparedStatement ps = con.prepareStatement("select * from users where uname=? and pass=?");

                ps.setString(1, uname);
                ps.setString(2, pass);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Features add = new Features();
                    this.dispose();
//                    add.show();
                    add.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username and password");
                    txtUser.setText("");
                    txtPass.setText("");
                    txtUser.requestFocus();
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HomePage::new);
    }
}