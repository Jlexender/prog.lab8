package ru.lexender.springcrud8gui.gui;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.lexender.springcrud8.dto.MovieDTO;
import ru.lexender.springcrud8.transfer.AuthResponse;
import ru.lexender.springcrud8gui.gui.visual.VisualPanel;
import ru.lexender.springcrud8gui.net.auth.AuthRestClient;
import ru.lexender.springcrud8gui.net.collection.CollectionRestClient;

import javax.swing.*;
import java.awt.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
@Log4j2
public class LoginFrame extends JFrame {

    public LoginFrame(AuthRestClient authRestClient,
                      BaseFrame baseFrame,
                      InfoFrame infoFrame,
                      CollectionRestClient collectionRestClient,
                      VisualPanel visualPanel) {
        super("Authorization");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(650, 250);

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);

        JButton submitButton = new JButton("Submit");
        submitButton.setPreferredSize(new Dimension(200, 20));

        // Radio buttons for login and registration
        JRadioButton loginRadio = new JRadioButton("Login");
        JRadioButton registerRadio = new JRadioButton("Register");
        loginRadio.setSelected(true); // Default selection

        // Button group for radio buttons
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(loginRadio);
        buttonGroup.add(registerRadio);

        submitButton.addActionListener(a -> {
            AuthResponse response = null;
            String pass = String.valueOf(passwordField.getPassword());
            try {
                if (loginRadio.isSelected()) {
                    response = authRestClient.login(usernameField.getText(), pass);
                } else if (registerRadio.isSelected()) {
                    response = authRestClient.register(usernameField.getText(), pass);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }


            JOptionPane.showMessageDialog(this, response.message(), "Message", JOptionPane.INFORMATION_MESSAGE);

            if (!response.invalid()) {
                try {
                    java.util.List<MovieDTO> movies = collectionRestClient.findAll();
                    infoFrame.getUsernameInfoLabel().setText(usernameField.getText());
                    baseFrame.getMovieTableModel().getMovieDTOS().clear();
                    baseFrame.getMovieTableModel().getMovieDTOS().addAll(movies);
                    baseFrame.getMovieTableModel().fireTableStructureChanged();
                    visualPanel.setObjects(movies);
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(this, exception.getMessage(), "Message", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add radio buttons for login and register
        panel.add(loginRadio, gbc);
        gbc.gridx++;
        panel.add(registerRadio, gbc);

        // Add username and password fields
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(usernameLabel, gbc);
        gbc.gridx++;
        panel.add(usernameField, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(passwordLabel, gbc);
        gbc.gridx++;
        panel.add(passwordField, gbc);

        // Add submit button
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2; // span across both columns
        panel.add(submitButton, gbc);

        getContentPane().add(panel, BorderLayout.CENTER);
    }


}
