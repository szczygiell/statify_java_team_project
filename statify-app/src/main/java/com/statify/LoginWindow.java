package com.statify;

import javax.swing.*;

import org.apache.commons.configuration2.plist.Token;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame implements ActionListener {

    Container container = getContentPane();
    JLabel passwordLabel = new JLabel("ACCESS TOKEN");
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("LOGIN");
    JButton resetButton = new JButton("RESET");
    JCheckBox showPassword = new JCheckBox("Show token");


    LoginWindow() {
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();

    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        container.setBackground(new java.awt.Color(52, 235, 107));
        passwordLabel.setBounds(50, 220, 100, 30);
        passwordLabel.setForeground(new java.awt.Color(0, 0, 0));
        passwordField.setBounds(150, 220, 150, 30);
        showPassword.setBounds(150, 250, 150, 30);
        showPassword.setBackground(new java.awt.Color(52, 235, 107));
        loginButton.setBounds(50, 300, 100, 30);
        loginButton.setBackground(new java.awt.Color(0, 0, 0));
        loginButton.setForeground(new java.awt.Color(255, 255, 255));
        resetButton.setBounds(200, 300, 100, 30);
        resetButton.setBackground(new java.awt.Color(0, 0, 0));
        resetButton.setForeground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(350, 540));


    }

    public void addComponentsToContainer() {
        container.add(passwordLabel);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton);
        container.add(resetButton);
    }

    public void addActionEvent() {
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        showPassword.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //Coding Part of LOGIN button
        if (e.getSource() == loginButton) {
            String pwdText;
            pwdText = passwordField.getText();
            TokenTest token_test = new TokenTest(pwdText);
            if (token_test.TokenTestFun()) {
                JOptionPane.showMessageDialog(this, "Login Successful");
                MainWindow main_window = new MainWindow();
                main_window.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Acces Token");
            }

        }
        //Coding Part of RESET button
        if (e.getSource() == resetButton) {
            passwordField.setText("");
        }
       //Coding Part of showPassword JCheckBox
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
        frame.setBounds(10, 10, 370, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

    }

}
