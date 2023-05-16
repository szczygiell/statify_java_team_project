package com.statify;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame implements ActionListener {

    // Container container = getContentPane();
    JLabel passwordLabel = new JLabel("ACCESS TOKEN");
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("LOGIN");
    JButton resetButton = new JButton("RESET");
    JCheckBox showPassword = new JCheckBox("Show token");
    JPanel backPanel = new GradientPanel();
    private String accessToken;
    public static int hight = 600;
    public static int width = 370;
    public static int bound = 100;

    LoginWindow() {
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }
    
    public int getHeight(){
        return hight;
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getBound(){
        return bound;
    }
    

    public void setLayoutManager() {
        // container.setLayout(null);
        backPanel.setLayout(null);
    }

    public String getAccessToken() {
        return accessToken;
    }

    private void setAccessToken(String token) {
        accessToken = token;
    }
 
    public void setLocationAndSize() {
        // container.setBackground(new java.awt.Color(29,185,84));
        passwordLabel.setBounds(40, 220, 150, 30);
        passwordLabel.setForeground(new java.awt.Color(0, 0, 0));
        passwordField.setBounds(150, 220, 150, 30);
        showPassword.setBounds(150, 250, 150, 30);
        // showPassword.setBackground(new java.awt.Color(29,185,84));
        showPassword.setOpaque(false);
        loginButton.setBounds(50, 300, 100, 30);
        loginButton.setBackground(new java.awt.Color(0, 0, 0));
        loginButton.setForeground(new java.awt.Color(255, 255, 255));
        resetButton.setBounds(200, 300, 100, 30);
        resetButton.setBackground(new java.awt.Color(0, 0, 0));
        resetButton.setForeground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(350, 540));

    }

    public void addComponentsToContainer() {
        // container.add(passwordLabel);
        // container.add(passwordField);
        // container.add(showPassword);
        // container.add(loginButton);
        // container.add(resetButton);

        backPanel.add(passwordLabel);
        backPanel.add(passwordField);
        backPanel.add(showPassword);
        backPanel.add(loginButton);
        backPanel.add(resetButton);
        backPanel.setVisible(true);
        add(backPanel);
    }

    public void addActionEvent() {
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        showPassword.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Coding Part of LOGIN button
        if (e.getSource() == loginButton) {
            String pwdText;
            pwdText = passwordField.getText();
            TokenTest token_test = new TokenTest(pwdText);
            if (token_test.TokenTestFun()) {
                JOptionPane.showMessageDialog(this, "Login Successful");
                Statify.setUser(new User(pwdText));
                MainWindow main_window = new MainWindow();
                main_window.setVisible(true);
                this.setVisible(false);
                this.remove(this);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Acces Token");
            }

        }
        // Coding Part of RESET button
        if (e.getSource() == resetButton) {
            passwordField.setText("");
        }
        // Coding Part of showPassword JCheckBox
        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }

        }
    }

    public static void main(String[] a) {
        LoginWindow frame = new LoginWindow();
        frame.setTitle("Login Form");
        frame.setVisible(true);
        frame.setBounds(frame.getBound(), frame.getBound(), frame.getWidth(), frame.getHeight());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        // frame.add(backPanel);
        // setContantePane()

    }

}
