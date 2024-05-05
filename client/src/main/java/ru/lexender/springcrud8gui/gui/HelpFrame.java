package ru.lexender.springcrud8gui.gui;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class HelpFrame extends JFrame {

    public HelpFrame() {
        super("Help window");

        JPanel mainPanel = new JPanel(new FlowLayout());

        add(mainPanel);

        setSize(450, 800);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }
}
