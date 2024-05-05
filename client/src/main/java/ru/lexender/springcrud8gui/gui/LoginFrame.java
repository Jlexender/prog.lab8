package ru.lexender.springcrud8gui.gui;

import org.springframework.stereotype.Component;
import ru.lexender.springcrud8.transfer.AuthResponse;
import ru.lexender.springcrud8gui.auth.AuthRestClient;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

@Component
public class LoginFrame extends JFrame {

    public LoginFrame(AuthRestClient authRestClient, BaseFrame baseFrame) {
        super("User login");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(600, 200);

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);

        JButton submitButton = new JButton("Submit");
        submitButton.setPreferredSize(new Dimension(200, 20));

        submitButton.addActionListener(a -> {
            String pass = String.valueOf(passwordField.getPassword());
             AuthResponse response =
                    authRestClient.login(usernameField.getText(), pass);
             JOptionPane.showMessageDialog(this, response.message(), "Message", JOptionPane.INFORMATION_MESSAGE);

             if (!response.invalid()) {
                 baseFrame.getUsernameInfoLabel().setText(usernameField.getText());
             }
        });

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);


        panel.add(usernameLabel, gbc);
        gbc.gridy++;
        panel.add(passwordLabel, gbc);
        gbc.gridy=0;
        gbc.gridx++;
        panel.add(usernameField, gbc);
        gbc.gridy++;
        panel.add(passwordField, gbc);

        gbc.gridy++;
        panel.add(submitButton, gbc);

        getContentPane().add(panel, BorderLayout.CENTER);
    }
}
