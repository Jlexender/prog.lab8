package ru.lexender.springcrud8gui.gui;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.springframework.stereotype.Component;
import ru.lexender.springcrud8.dto.CoordinatesDTO;
import ru.lexender.springcrud8.dto.MovieDTO;
import ru.lexender.springcrud8.dto.MovieGenre;
import ru.lexender.springcrud8.dto.PersonDTO;
import ru.lexender.springcrud8.dto.UserdataDTO;
import ru.lexender.springcrud8.transfer.AuthResponse;
import ru.lexender.springcrud8.transfer.CommandResponse;
import ru.lexender.springcrud8gui.gui.model.MovieTableModel;
import ru.lexender.springcrud8gui.gui.visual.VisualFrame;
import ru.lexender.springcrud8gui.gui.visual.VisualPanel;
import ru.lexender.springcrud8gui.net.command.CommandRestClient;

import javax.swing.*;
import javax.swing.text.DefaultFormatter;
import java.awt.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;

/**
 * the whole code here is fucking awful
 * I don't even have a goddamn clue
 * how people should make this look ok
 */

@Component
public class AddFrame extends JFrame {

    private final MovieTableModel movieTableModel;

    public AddFrame(CommandRestClient commandRestClient, VisualPanel visualPanel, HelpFrame helpFrame, BaseFrame baseFrame, MovieTableModel movieTableModel) {
        super("Add Movie");

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(450, 450);

        // Создаем панель для размещения компонентов
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 10, 10)); // Сетка 2 столбца, отступы 10 пикселей

        // Добавляем метки и поля ввода с отступами
        panel.add(new JLabel("Name:"));
        panel.add(new JTextField());

        panel.add(new JLabel("Coordinate X:"));
        panel.add(new NumericTextField());

        panel.add(new JLabel("Coordinate Y:"));
        panel.add(new NumericTextField());

        panel.add(new JLabel("Oscars Count:"));
        panel.add(new NumericTextField());

        panel.add(new JLabel("Golden Palms Count:"));
        panel.add(new NumericTextField());

        panel.add(new JLabel("Length:"));
        panel.add(new NumericTextField());

        panel.add(new JLabel("Genre:"));
        JComboBox<MovieGenre> genreComboBox = new JComboBox<>(MovieGenre.values());
        panel.add(genreComboBox);

        panel.add(new JLabel("Operator Name:"));
        panel.add(new JTextField());

        panel.add(new JLabel("Operator Birthday:"));
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());

        panel.add(datePicker);

        panel.add(new JLabel("Operator Height:"));
        panel.add(new NumericTextField());

        // Создаем кнопку "Add"
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            try {
                // Получаем значения из текстовых полей
                String name = ((JTextField) panel.getComponent(1)).getText();
                float coordinateX = Float.parseFloat(((JTextField) panel.getComponent(3)).getText());
                int coordinateY = Integer.parseInt(((JTextField) panel.getComponent(5)).getText());
                long oscarsCount = Integer.parseInt(((JTextField) panel.getComponent(7)).getText());
                int goldenPalmsCount = Integer.parseInt(((JTextField) panel.getComponent(9)).getText());
                int length = Integer.parseInt(((JTextField) panel.getComponent(11)).getText());
                String genre = ((JComboBox<?>) panel.getComponent(13)).getSelectedItem().toString();
                String operatorName = ((JTextField) panel.getComponent(15)).getText();
                // Получаем выбранную дату из JDatePickerImpl
                Date selectedDate = (Date) datePicker.getModel().getValue();

                // Преобразуем Date в ZonedDateTime
                Instant instant = selectedDate.toInstant();
                ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault()); // Преобразование в системную временную зону, замените на нужную вам
                double operatorHeight = Double.parseDouble(((JTextField) panel.getComponent(19)).getText());



                MovieDTO movieDTO = MovieDTO.builder()
                        .name(name)
                        .coordinates(CoordinatesDTO.builder()
                                .x(coordinateX)
                                .y(coordinateY)
                                .build())
                        .oscarsCount(oscarsCount)
                        .goldenPalmCount(goldenPalmsCount)
                        .length(length)
                        .genre(MovieGenre.valueOf(genre))
                        .operator(PersonDTO.builder()
                                .name(operatorName)
                                .birthday(zonedDateTime)
                                .height(operatorHeight)
                                .build())
                        .author(new UserdataDTO(helpFrame.getUsernameInfoLabel().getText()))
                        .build();

                // Отправляем запрос с помощью CommandRestClient
                CommandResponse r = commandRestClient.query("add", java.util.List.of(movieDTO));
                visualPanel.addObject(movieDTO);
                movieTableModel.getMovieDTOS().add(movieDTO);
                movieTableModel.fireTableDataChanged();

                JOptionPane.showMessageDialog(AddFrame.this, "Added", "Info", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(AddFrame.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Добавляем кнопку "Add" на панель
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addButton);

        // Добавляем панель с кнопкой внизу фрейма
        add(buttonPanel, BorderLayout.SOUTH);

        // Добавляем панель с метками и полями ввода на фрейм
        add(panel);
        this.movieTableModel = movieTableModel;
    }
}
