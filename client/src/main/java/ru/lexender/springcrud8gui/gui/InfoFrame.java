package ru.lexender.springcrud8gui.gui;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.lexender.springcrud8gui.gui.localization.LocalizationService;
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
public class InfoFrame extends JFrame {
    private final LocalizationService localizationService;
    JLabel usernameInfoLabel = new JLabel(), dateTimeLabel, usernameLabel, tokenInfoLabel, tokenLabel = new JLabel();
    JButton tokenButton;

    public InfoFrame(LocalizationService localizationService) {
        super(localizationService.get("info.frame.title"));
        this.localizationService = localizationService;

        dateTimeLabel = new JLabel(localizationService.get("label.dateandtime"));
        usernameLabel = new JLabel(localizationService.get("label.username"));
        tokenInfoLabel = new JLabel(localizationService.get("label.token"));
        tokenButton = createTokenButton(tokenLabel);
    }

    @PostConstruct
    private void init() {
        JPanel infoPanel = new JPanel(new GridLayout(4, 2));

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
                JOptionPane.showMessageDialog(InfoFrame.this,
                        localizationService.get("notification.copiedtoclipboard"),
                        localizationService.get("notification.title"),
                        JOptionPane.INFORMATION_MESSAGE);
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
        repaint();
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
        tokenButton = new JButton(localizationService.get("button.showtoken"));
        tokenButton.addActionListener(e -> tokenLabel.setText(NetConfiguration.authToken));
        return tokenButton;
    }

    public void refreshUI() {
        dateTimeLabel.setText(localizationService.get("label.dateandtime"));
        usernameLabel.setText(localizationService.get("label.username"));
        tokenInfoLabel.setText(localizationService.get("label.token"));
        tokenButton.setText(localizationService.get("button.showtoken"));
        super.setTitle(localizationService.get("info.frame.title"));
    }
}
