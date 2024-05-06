package ru.lexender.springcrud8gui.gui.visual;

import org.springframework.stereotype.Component;
import ru.lexender.springcrud8.dto.MovieDTO;

import javax.swing.*;
import java.util.List;

@Component
public class VisualFrame extends JFrame {

    public VisualFrame(VisualPanel visualPanel) {
        setTitle("Visualization");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        add(visualPanel);
    }
}
