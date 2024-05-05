package ru.lexender.springcrud8gui.gui;

import lombok.Getter;
import org.springframework.stereotype.Component;
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
@Getter
public class HelpFrame extends JFrame {
    JLabel usernameInfoLabel = new JLabel();

    public HelpFrame() {
        super("Help window");

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



        tokenLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                tokenLabel.requestFocusInWindow();
                StringSelection stringSelection = new StringSelection(tokenLabel.getText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
                JOptionPane.showMessageDialog(HelpFrame.this, "Copied to clipboard", "Message", JOptionPane.INFORMATION_MESSAGE);
                tokenLabel.setText("");
            }
        });

        infoPanel.add(dateTimeLabel);
        infoPanel.add(createDateTimeLabel());
        infoPanel.add(usernameLabel);
        infoPanel.add(usernameInfoLabel);
        infoPanel.add(tokenInfoLabel);
        infoPanel.add(tokenLabel);
        infoPanel.add(createTokenButton(tokenLabel));

        add(infoPanel);

        setSize(450, 600);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    JLabel createDateTimeLabel() {
        JLabel dateTimeLabel = new JLabel();
        updateDateTimeLabel(dateTimeLabel);
        Timer timer = new Timer(1000, e -> updateDateTimeLabel(dateTimeLabel));
        timer.start();
        return dateTimeLabel;
    }

    void updateDateTimeLabel(JLabel label) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        label.setText(now.format(formatter));
    }

    JButton createTokenButton(JLabel tokenLabel) {
        JButton tokenButton = new JButton("Show Token");
        tokenButton.addActionListener(e -> tokenLabel.setText(NetConfiguration.authToken));
        return tokenButton;
    }
}
