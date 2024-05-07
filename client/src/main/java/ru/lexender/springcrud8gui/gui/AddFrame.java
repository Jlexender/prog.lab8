package ru.lexender.springcrud8gui.gui;

import jakarta.annotation.PostConstruct;
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
import ru.lexender.springcrud8.transfer.CommandResponse;
import ru.lexender.springcrud8gui.gui.localization.LocalizationService;
import ru.lexender.springcrud8gui.gui.model.MovieTableModel;
import ru.lexender.springcrud8gui.gui.visual.VisualPanel;
import ru.lexender.springcrud8gui.net.command.CommandRestClient;

import javax.swing.*;
import java.awt.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
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
    private final LocalizationService localizationService;
    private final CommandRestClient commandRestClient;
    private final VisualPanel visualPanel;
    private final InfoFrame infoFrame;
    private JLabel nameLabel,
            cxLabel, cyLabel, oscarsLabel,
            gpalmsLabel, lengthLabel, opNameLabel,
            opBdLabel, opHLabel, genreLabel;
    private JButton addButton;
    private JDatePanelImpl datePanel;
    private UtilDateModel model;

    public AddFrame(CommandRestClient commandRestClient,
                    VisualPanel visualPanel,
                    InfoFrame infoFrame,
                    MovieTableModel movieTableModel, LocalizationService localizationService) {
        super(localizationService.get("add.frame.title"));

        this.commandRestClient = commandRestClient;
        this.visualPanel = visualPanel;
        this.infoFrame = infoFrame;
        this.movieTableModel = movieTableModel;
        this.localizationService = localizationService;
    }

    @PostConstruct
    private void init() {
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(450, 450);

        // Создаем панель для размещения компонентов
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 10, 10)); // Сетка 2 столбца, отступы 10 пикселей

        // Добавляем метки и поля ввода с отступами

        nameLabel = new JLabel(localizationService.get("movie.fields.name"));
        panel.add(nameLabel);
        panel.add(new JTextField());

        cxLabel = new JLabel(localizationService.get("movie.fields.coordx"));
        panel.add(cxLabel);
        panel.add(new NumericTextField(true));

        cyLabel = new JLabel(localizationService.get("movie.fields.coordy"));
        panel.add(cyLabel);
        panel.add(new NumericTextField(false));

        oscarsLabel = new JLabel(localizationService.get("movie.fields.oscars"));
        panel.add(oscarsLabel);
        panel.add(new NumericTextField(false));

        gpalmsLabel = new JLabel(localizationService.get("movie.fields.gpalms"));
        panel.add(gpalmsLabel);
        panel.add(new NumericTextField(false));

        lengthLabel = new JLabel(localizationService.get("movie.fields.length"));
        panel.add(lengthLabel);
        panel.add(new NumericTextField(false));

        genreLabel = new JLabel(localizationService.get("movie.fields.genre"));
        panel.add(genreLabel);
        JComboBox<MovieGenre> genreComboBox = new JComboBox<>(MovieGenre.values());
        panel.add(genreComboBox);

        opNameLabel = new JLabel(localizationService.get("movie.fields.opname"));
        panel.add(opNameLabel);
        panel.add(new JTextField());

        opBdLabel = new JLabel(localizationService.get("movie.fields.opbd"));
        panel.add(opBdLabel);
        model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", localizationService.get("calendar.today"));
        p.put("text.month", localizationService.get("calendar.month"));
        p.put("text.year", localizationService.get("calendar.year"));
        datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());

        panel.add(datePicker);

        opHLabel = new JLabel(localizationService.get("movie.fields.oph"));
        panel.add(opHLabel);
        panel.add(new NumericTextField(true));

        // Создаем кнопку "Add"
        addButton = new JButton(localizationService.get("button.add"));
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
                        .author(new UserdataDTO(infoFrame.getUsernameInfoLabel().getText()))
                        .build();

                // Отправляем запрос с помощью CommandRestClient
                CommandResponse r = commandRestClient.query("add", java.util.List.of(movieDTO));
                movieTableModel.getMovieDTOS().add(movieDTO);

                JOptionPane.showMessageDialog(AddFrame.this, localizationService.get("addcmd.added"),
                        localizationService.get("information.title"), JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(AddFrame.this, ex.getMessage(),
                        localizationService.get("error.title"), JOptionPane.ERROR_MESSAGE);
            }
        });

        // Добавляем кнопку "Add" на панель
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addButton);

        // Добавляем панель с кнопкой внизу фрейма
        add(buttonPanel, BorderLayout.SOUTH);

        // Добавляем панель с метками и полями ввода на фрейм
        add(panel);
    }

    public void refreshUI() {
        addButton.setText(localizationService.get("button.add"));
        nameLabel.setText(localizationService.get("movie.fields.name"));
        opNameLabel.setText(localizationService.get("movie.fields.opname"));
        opHLabel.setText(localizationService.get("movie.fields.oph"));
        opBdLabel.setText(localizationService.get("movie.fields.opbd"));
        lengthLabel.setText(localizationService.get("movie.fields.length"));
        gpalmsLabel.setText(localizationService.get("movie.fields.gpalms"));
        oscarsLabel.setText(localizationService.get("movie.fields.oscars"));
        cxLabel.setText(localizationService.get("movie.fields.coordx"));
        cyLabel.setText(localizationService.get("movie.fields.coordy"));
        genreLabel.setText(localizationService.get("movie.fields.genre"));
        super.setTitle(localizationService.get("add.frame.title"));
    }
}
