package ru.lexender.springcrud8gui.gui;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.lexender.springcrud8.dto.MovieDTO;
import ru.lexender.springcrud8.transfer.AuthResponse;
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
                      HelpFrame helpFrame,
                      CollectionRestClient collectionRestClient) {
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
                 try {
                     java.util.List<MovieDTO> movies = collectionRestClient.findAll();
                     helpFrame.getUsernameInfoLabel().setText(usernameField.getText());
                     baseFrame.getMovieTableModel().getMovieDTOS().clear();
                     baseFrame.getMovieTableModel().getMovieDTOS().addAll(movies);
                     baseFrame.getMovieTableModel().fireTableStructureChanged();
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
