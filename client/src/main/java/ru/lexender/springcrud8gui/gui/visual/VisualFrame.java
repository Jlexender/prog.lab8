package ru.lexender.springcrud8gui.gui.visual;

import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class VisualFrame extends JFrame {

    public VisualFrame(VisualPanel visualPanel) {
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        add(visualPanel);
    }
}
