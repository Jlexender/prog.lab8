package ru.lexender.springcrud8gui.gui;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.lexender.springcrud8.transfer.CommandResponse;
import ru.lexender.springcrud8gui.command.CommandRestClient;
import ru.lexender.springcrud8gui.net.NetConfiguration;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class BaseFrame extends JFrame {
    JLabel usernameInfoLabel = new JLabel();

    @Lazy
    public BaseFrame(LoginFrame loginFrame,
                     HelpFrame helpFrame,
                     CommandRestClient commandRestClient) {
        super("Collection");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        JTable table = new JTable(10, 5);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tabbedPane.addTab("Table", tableScrollPane);

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

        JPanel infoPanel = new JPanel(new GridLayout(4, 2));

        JLabel dateTimeLabel = new JLabel("Date and Time:");
        JLabel usernameLabel = new JLabel("Username:");
        JLabel tokenInfoLabel = new JLabel("Token:");
        JLabel tokenLabel = new JLabel();

        Font plainFont = new Font(Font.DIALOG, Font.PLAIN, 12);
        dateTimeLabel.setFont(plainFont);
        usernameLabel.setFont(plainFont);
        tokenInfoLabel.setFont(plainFont);
        tokenLabel.setFont(plainFont);
        tokenLabel.setForeground(Color.BLUE);

        infoPanel.add(dateTimeLabel);
        infoPanel.add(createDateTimeLabel());
        infoPanel.add(usernameLabel);
        infoPanel.add(usernameInfoLabel);
        infoPanel.add(tokenInfoLabel);
        infoPanel.add(tokenLabel);
        infoPanel.add(createTokenButton(tokenLabel));

        tokenLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                tokenLabel.requestFocusInWindow();
                StringSelection stringSelection = new StringSelection(tokenLabel.getText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
                JOptionPane.showMessageDialog(BaseFrame.this, "Copied to clipboard", "Message", JOptionPane.INFORMATION_MESSAGE);
                tokenLabel.setText("");
            }
        });

        JPanel languagePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel languageLabel = new JLabel("Choose language:");
        JComboBox<String> languageComboBox = new JComboBox<>(new String[]{"English", "Русский"});
        languagePanel.add(languageLabel);
        languagePanel.add(languageComboBox);

        add(languagePanel, BorderLayout.NORTH);

        tabbedPane.addTab("Info", infoPanel);

        add(tabbedPane, BorderLayout.CENTER);

        JButton helpButton = new JButton("Help");
        JButton loginButton = new JButton("Login");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(helpButton);
        buttonPanel.add(loginButton);
        add(buttonPanel, BorderLayout.SOUTH);

        loginButton.addActionListener(e -> {
            loginFrame.setVisible(true);
        });

        helpButton.addActionListener(e -> {
            helpFrame.setVisible(true);
        });

        submitButton.addActionListener(e -> {
            CommandResponse response = commandRestClient.query(inputField.getText(), null);
            serverResponseArea.append("Server message:\n" + response.message() + '\n');
            inputField.setText("");
        });

        setSize(800, 600);
        setVisible(true);
    }

    private JLabel createDateTimeLabel() {
        JLabel dateTimeLabel = new JLabel();
        updateDateTimeLabel(dateTimeLabel);
        Timer timer = new Timer(1000, e -> updateDateTimeLabel(dateTimeLabel));
        timer.start();
        return dateTimeLabel;
    }

    private void updateDateTimeLabel(JLabel label) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        label.setText(now.format(formatter));
    }

    private JButton createTokenButton(JLabel tokenLabel) {
        JButton tokenButton = new JButton("Token");
        tokenButton.addActionListener(e -> tokenLabel.setText(NetConfiguration.authToken));
        return tokenButton;
    }
}
