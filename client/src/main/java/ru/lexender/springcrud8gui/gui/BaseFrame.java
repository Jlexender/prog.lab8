package ru.lexender.springcrud8gui.gui;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.lexender.springcrud8.transfer.CommandResponse;
import ru.lexender.springcrud8gui.gui.model.MovieTableModel;
import ru.lexender.springcrud8gui.gui.visual.VisualFrame;
import ru.lexender.springcrud8gui.net.command.CommandRestClient;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class BaseFrame extends JFrame {
    private final AddFrame addFrame;
    JFrame helpFrame;
    JFrame loginFrame;
    CommandRestClient commandRestClient;
    MovieTableModel movieTableModel;
    VisualFrame visualFrame;
    JTable table;

    @Lazy
    public BaseFrame(LoginFrame loginFrame,
                     InfoFrame infoFrame,
                     CommandRestClient commandRestClient,
                     MovieTableModel movieTableModel,
                     VisualFrame visualFrame, AddFrame addFrame) {
        super("Collection");
        this.loginFrame = loginFrame;
        this.helpFrame = infoFrame;
        this.commandRestClient = commandRestClient;
        this.movieTableModel = movieTableModel;
        this.visualFrame = visualFrame;
        this.addFrame = addFrame;
        table = new JTable(10, 5);

    }



    @PostConstruct
    void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        table.setModel(movieTableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tabbedPane.addTab("Collection", tableScrollPane);

        JPanel consolePanel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField inputField = new JTextField(20);
        JButton submitButton = new JButton("Submit");
        inputPanel.add(inputField);
        inputPanel.add(submitButton);
        consolePanel.add(inputPanel, BorderLayout.NORTH);

        JTextArea serverResponseArea = new JTextArea();
        serverResponseArea.setEditable(false);
        JScrollPane responseScrollPane = new JScrollPane(serverResponseArea);
        consolePanel.add(responseScrollPane, BorderLayout.CENTER);

        tabbedPane.addTab("Console", consolePanel);

        JPanel languagePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel languageLabel = new JLabel("Choose language:");
        JComboBox<String> languageComboBox = new JComboBox<>(new String[]{"English", "Русский"});
        languagePanel.add(languageLabel);
        languagePanel.add(languageComboBox);

        add(languagePanel, BorderLayout.NORTH);

        add(tabbedPane, BorderLayout.CENTER);

        JButton helpButton = new JButton("Info");
        JButton loginButton = new JButton("Authorization");
        JButton visualButton = new JButton("Visual"); // Add Visual button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(helpButton);
        buttonPanel.add(loginButton);
        buttonPanel.add(visualButton); // Add Visual button
        add(buttonPanel, BorderLayout.SOUTH);

        loginButton.addActionListener(e -> {
            loginFrame.setVisible(true);
        });

        helpButton.addActionListener(e -> {
            helpFrame.setVisible(true);
        });

        visualButton.addActionListener(e -> {
            visualFrame.setVisible(true); // Show VisualFrame
        });


        submitButton.addActionListener(e -> {
            try {
                CommandResponse response = commandRestClient.query(inputField.getText(), null);
                serverResponseArea.append("Server message:\n" + response.message() + '\n');
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(BaseFrame.this, "Server is unavailable", "Error", JOptionPane.ERROR_MESSAGE);
            }
            inputField.setText("");
        });

        TableRowSorter<MovieTableModel> sorter = new TableRowSorter<>(movieTableModel);
        table.setRowSorter(sorter);


        JButton addButton = new JButton("Add");
        buttonPanel.add(addButton); // Добавляем кнопку Add в панель с кнопками

        addButton.addActionListener(e -> {
            addFrame.setVisible(true);
        });

        setSize(1200, 800);
        setVisible(true);
    }
}
