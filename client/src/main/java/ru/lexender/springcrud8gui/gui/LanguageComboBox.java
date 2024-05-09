package ru.lexender.springcrud8gui.gui;

import lombok.Getter;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.swing.*;
import java.util.Locale;

@Getter
public class LanguageComboBox extends JComboBox<String> {
    private final BaseFrame baseFrame;

    public LanguageComboBox(BaseFrame baseFrame) {
        super();
        addItem("English");
        addItem("Русский");
        addItem("Română");
        addItem("Hrvatski");

        this.baseFrame = baseFrame;

        // Устанавливаем текущий выбранный элемент в соответствии с текущим языком
        Locale currentLocale = Locale.getDefault();
        setSelectedItem(currentLocale.getLanguage());

        // Добавляем обработчик для выбора элемента из списка
        addActionListener(e -> {
            // Получаем выбранный элемент
            String selectedLanguage = (String) getSelectedItem();
            // Устанавливаем язык приложения в соответствии с выбранным элементом
            setLanguage(selectedLanguage);
        });

    }

    private void setLanguage(String language) {
        // Определяем новый Locale на основе выбранного языка
        Locale newLocale = switch (language) {
            case "Русский" -> Locale.of("ru");
            case "Română" -> Locale.of("ro");
            case "Hrvatski" -> Locale.of("hr");
            default -> Locale.ENGLISH;
        };
        // Устанавливаем новый Locale в качестве языка приложения
        LocaleContextHolder.setLocale(newLocale);

        baseFrame.refreshUI();
        baseFrame.getHelpFrame().refreshUI();
        baseFrame.getAddFrame().refreshUI();
        baseFrame.getLoginFrame().refreshUI();
        baseFrame.getMovieTableModel().update();
    }
}
