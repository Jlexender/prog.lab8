package ru.lexender.springcrud8gui.gui.visual;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.lexender.springcrud8.dto.CoordinatesDTO;
import ru.lexender.springcrud8.dto.MovieDTO;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class VisualPanel extends JPanel {
    private final Map<String, Color> authorColorMap = new HashMap<>();

    @Getter private ArrayList<MovieDTO> objects = new ArrayList<>();

    public VisualPanel() {
        setBackground(Color.WHITE);
        addMouseListener(new ObjectClickListener());
    }

    public void addObject(MovieDTO object) {
        this.objects.add(object);
        repaint();
    }

    public void setObjects(List<MovieDTO> objects) {
        this.objects.addAll(objects);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (MovieDTO obj : objects) {
            String author = obj.getAuthor().getUsername();
            Color authorColor = authorColorMap.get(author);
            if (authorColor == null) {
                authorColor = generateRandomColor(author);
                authorColorMap.put(author, authorColor);
            }
            g.setColor(authorColor);
            g.fillRect(Math.round(obj.getCoordinates().getX()), obj.getCoordinates().getY(), 50, 50);
        }
    }

    private Color generateRandomColor(String author) {
        // Use a simple hash function to generate a color based on the author's name
        int hash = author.hashCode();
        // Ensure hash is positive
        hash = hash >= 0 ? hash : -hash;
        // Use the lower 24 bits of the hash to generate RGB values
        int r = (hash & 0xFF0000) >> 16;
        int g = (hash & 0x00FF00) >> 8;
        int b = hash & 0x0000FF;
        // Create and return the color
        return new Color(r, g, b);
    }

    private class ObjectClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int mouseX = e.getX();
            int mouseY = e.getY();
            for (MovieDTO obj : objects) {
                CoordinatesDTO coordinates = obj.getCoordinates();
                if (mouseX >= coordinates.getX() && mouseX <= coordinates.getX() + 50
                        && mouseY >= coordinates.getY() && mouseY <= coordinates.getY() + 50) {
                    String message = """
                            Object info:
                            id: %d
                            name: %s
                            coordinate x: %f
                            coordinate y: %f
                            creation date: %s
                            oscars: %d
                            golden palms: %d
                            length: %d
                            genre: %s
                            operator name: %s
                            operator birthday: %s
                            operator height: %f
                            object author: %s
                            """.formatted(obj.getId(), obj.getName(),
                            obj.getCoordinates().getX(), Float.valueOf(obj.getCoordinates().getY()), obj.getCreationDate(), obj.getOscarsCount(),
                            obj.getGoldenPalmCount(), obj.getLength(), obj.getGenre(), obj.getOperator().getName(),
                            obj.getOperator().getBirthday(), obj.getOperator().getHeight(), obj.getAuthor().getUsername());

                    JOptionPane.showMessageDialog(VisualPanel.this, message, "Object Info", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }
        }
    }
}
