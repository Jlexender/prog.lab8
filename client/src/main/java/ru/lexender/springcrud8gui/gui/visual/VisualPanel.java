package ru.lexender.springcrud8gui.gui.visual;

import org.springframework.stereotype.Component;
import ru.lexender.springcrud8.dto.CoordinatesDTO;
import ru.lexender.springcrud8.dto.MovieDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class VisualPanel extends JPanel {
    private final Map<String, Color> authorColorMap = new HashMap<>();
    private ArrayList<MovieDTO> objects = new ArrayList<>();
    private int xOffset = 0; // Horizontal offset for navigation
    private int yOffset = 0; // Vertical offset for navigation
    private int lastX; // Last known mouse X position
    private int lastY; // Last known mouse Y position


    public VisualPanel() {
        setBackground(Color.WHITE);
        addMouseListener(new ObjectClickListener());
        addMouseMotionListener(new MouseMotionAdapter());
    }

    public void addObject(MovieDTO object) {
        this.objects.add(object);
        repaint();
    }

    public void setObjects(java.util.List<MovieDTO> objects) {
        this.objects.addAll(objects);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        // Set transparency level to 50%
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
        g2d.setComposite(alphaComposite);

        for (MovieDTO obj : objects) {
            String author = obj.getAuthor().getUsername();
            Color authorColor = authorColorMap.get(author);
            if (authorColor == null) {
                authorColor = generateRandomColor(author);
                authorColorMap.put(author, authorColor);
            }
            g2d.setColor(authorColor);

            // Calculate radius based on Oscars count
            int radius = 25 + 5*obj.getOscarsCount().intValue(); // Adjust the base radius as needed

            // Draw oval with transparency
            g2d.fillOval(Math.round(obj.getCoordinates().getX()) - radius - xOffset,
                    obj.getCoordinates().getY() - radius - yOffset,
                    2 * radius, 2 * radius);

            // Calculate center coordinates of the oval
            int centerX = Math.round(obj.getCoordinates().getX()) - xOffset;
            int centerY = obj.getCoordinates().getY() - yOffset;

            // Draw clickable area as a hollow circle
            g2d.setColor(Color.WHITE);
            var clickableRadius = 20;
            g2d.drawOval(centerX - clickableRadius, centerY - clickableRadius, 2 * clickableRadius, 2 * clickableRadius);
        }

        g2d.dispose();
    }

    private Color generateRandomColor(String author) {
        Random random = new Random(author.hashCode());
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color(r, g, b);
    }

    private class ObjectClickListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            // Save mouse press position
            lastX = e.getX();
            lastY = e.getY();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // Reset last known position when mouse is released
            lastX = -1;
            lastY = -1;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // Check if the click was within an object
            int mouseX = e.getX() + xOffset;
            int mouseY = e.getY() + yOffset;
            for (MovieDTO obj : objects) {
                CoordinatesDTO coordinates = obj.getCoordinates();
                if (mouseX >= coordinates.getX() - 25 && mouseX <= coordinates.getX() + 75
                        && mouseY >= coordinates.getY() - 25 && mouseY <= coordinates.getY() + 75) {
                    // Display information about the clicked object
                    String message = String.format("Object info:\n" +
                                    "Name: %s\n" +
                                    "Coordinates: (%f, %d)\n" +
                                    "Creation Date: %s\n" +
                                    "Oscars: %d\n" +
                                    "Golden Palms: %d\n" +
                                    "Length: %d\n" +
                                    "Genre: %s\n" +
                                    "Operator Name: %s\n" +
                                    "Operator Birthday: %s\n" +
                                    "Operator Height: %.2f\n" +
                                    "Object Author: %s",
                            obj.getName(), obj.getCoordinates().getX(), obj.getCoordinates().getY(),
                            obj.getCreationDate(), obj.getOscarsCount(), obj.getGoldenPalmCount(),
                            obj.getLength(), obj.getGenre(), obj.getOperator().getName(),
                            obj.getOperator().getBirthday(), obj.getOperator().getHeight(),
                            obj.getAuthor().getUsername());
                    JOptionPane.showMessageDialog(VisualPanel.this, message, "Object Info", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }
        }
    }

    private class MouseMotionAdapter extends MouseAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            // Calculate movement offset based on current and last known mouse position
            int dx = e.getX() - lastX;
            int dy = e.getY() - lastY;
            // Update offsets
            xOffset += dx;
            yOffset += dy;
            // Update last known position
            lastX = e.getX();
            lastY = e.getY();
            // Repaint panel to show changes
            repaint();
        }
    }
}







