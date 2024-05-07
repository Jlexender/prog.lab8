package ru.lexender.springcrud8gui.gui;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.lexender.springcrud8.dto.MovieDTO;
import ru.lexender.springcrud8.transfer.CommandResponse;
import ru.lexender.springcrud8gui.gui.localization.LocalizationService;
import ru.lexender.springcrud8gui.gui.model.MovieTableModel;
import ru.lexender.springcrud8gui.gui.visual.VisualFrame;
import ru.lexender.springcrud8gui.net.NetConfiguration;
import ru.lexender.springcrud8gui.net.collection.CollectionRestClient;
import ru.lexender.springcrud8gui.net.command.CommandRestClient;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@Log4j2
public class BaseFrame extends JFrame {
    private java.util.List<java.awt.Component> allComponents = new ArrayList<>();
    private final AddFrame addFrame;
    InfoFrame helpFrame;
    LoginFrame loginFrame;
    CommandRestClient commandRestClient;
    MovieTableModel movieTableModel;
    VisualFrame visualFrame;
    JTable table;
    LocalizationService localizationService;
    JButton submitButton, helpButton, visualButton, loginButton, addButton;
    JLabel languageLabel;
    JTabbedPane tabbedPane;
    CollectionRestClient collectionRestClient;

    @Lazy
    public BaseFrame(LoginFrame loginFrame,
                     InfoFrame infoFrame,
                     CommandRestClient commandRestClient,
                     MovieTableModel movieTableModel,
                     VisualFrame visualFrame,
                     AddFrame addFrame,
                     LocalizationService localizationService,
                     CollectionRestClient collectionRestClient) {
        super(localizationService.get("base.frame.title"));
        this.loginFrame = loginFrame;
        this.helpFrame = infoFrame;
        this.commandRestClient = commandRestClient;
        this.movieTableModel = movieTableModel;
        this.visualFrame = visualFrame;
        this.addFrame = addFrame;
        this.localizationService = localizationService;
        this.collectionRestClient = collectionRestClient;
        table = new JTable();
    }



    @PostConstruct
    void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();

        table.setModel(movieTableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);

        tabbedPane.addTab(localizationService.get("base.panel.tab"), tableScrollPane);

        JPanel consolePanel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField inputField = new JTextField(20);
        submitButton = new JButton(localizationService.get("button.submit"));

        inputPanel.add(inputField);
        inputPanel.add(submitButton);
        consolePanel.add(inputPanel, BorderLayout.NORTH);

        JTextArea serverResponseArea = new JTextArea();
        serverResponseArea.setEditable(false);
        JScrollPane responseScrollPane = new JScrollPane(serverResponseArea);
        consolePanel.add(responseScrollPane, BorderLayout.CENTER);

        tabbedPane.addTab(localizationService.get("button.console"), consolePanel);

        JPanel languagePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        languageLabel = new JLabel(localizationService.get("label.choose.language"));
        LanguageComboBox languageComboBox = new LanguageComboBox(this);
        languagePanel.add(languageLabel);
        languagePanel.add(languageComboBox);

        add(languagePanel, BorderLayout.NORTH);

        add(tabbedPane, BorderLayout.CENTER);

        helpButton = new JButton(localizationService.get("button.info"));
        loginButton = new JButton(localizationService.get("button.authorization"));
        visualButton = new JButton(localizationService.get("button.visual")); // Add Visual button

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
                serverResponseArea.append(localizationService.get("notification.servermsg") + '\n' + response.message() + '\n');
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(BaseFrame.this,
                        localizationService.get("error.serverunavailable"),
                        localizationService.get("error.title"), JOptionPane.ERROR_MESSAGE);
            }
            inputField.setText("");
        });

        TableRowSorter<MovieTableModel> sorter = new TableRowSorter<>(movieTableModel);
        table.setRowSorter(sorter);


        addButton = new JButton(localizationService.get("button.add"));
        buttonPanel.add(addButton); // Добавляем кнопку Add в панель с кнопками

        addButton.addActionListener(e -> {
            addFrame.setVisible(true);
        });



        setSize(1200, 800);
        setVisible(true);
    }

    public void refreshUI() {
        super.setTitle(localizationService.get("base.frame.title"));
        helpButton.setText(localizationService.get("button.info"));
        loginButton.setText(localizationService.get("button.authorization"));
        visualButton.setText(localizationService.get("button.visual"));
        submitButton.setText(localizationService.get("button.submit"));
        addButton.setText(localizationService.get("button.add"));
        tabbedPane.setTitleAt(0, localizationService.get("base.panel.tab"));
        tabbedPane.setTitleAt(1, localizationService.get("button.console"));
    }

    public void refreshTable() throws Exception {
        if (NetConfiguration.authToken != null) {
            movieTableModel.getMovieDTOS().clear();
            movieTableModel.getMovieDTOS().addAll(collectionRestClient.findAll());
            movieTableModel.fireTableDataChanged();
        }
    }

}
