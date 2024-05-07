package ru.lexender.springcrud8gui.gui.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.lexender.springcrud8.dto.MovieDTO;
import ru.lexender.springcrud8.dto.MovieGenre;
import ru.lexender.springcrud8gui.gui.localization.LocalizationService;
import ru.lexender.springcrud8gui.net.collection.CollectionRestClient;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@Component
@Setter
public class MovieTableModel extends AbstractTableModel {
    List<MovieDTO> movieDTOS = new LinkedList<>();
    @NonFinal List<String> columnNames = new LinkedList<>();
    LocalizationService localizationService;
    CollectionRestClient collectionRestClient;

    @Autowired
    public MovieTableModel(LocalizationService localizationService,
                           CollectionRestClient collectionRestClient) {
        this.localizationService = localizationService;
        this.collectionRestClient = collectionRestClient;
        update();
    }

    @Override
    public int getRowCount() {
        return movieDTOS.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        MovieDTO movie = movieDTOS.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> movie.getId();
            case 1 -> movie.getName();
            case 2 -> movie.getCoordinates().getX();
            case 3 -> movie.getCoordinates().getY();
            case 4 -> movie.getCreationDate();
            case 5 -> movie.getOscarsCount();
            case 6 -> movie.getGoldenPalmCount();
            case 7 -> movie.getLength();
            case 8 -> movie.getGenre();
            case 9 -> movie.getOperator().getName();
            case 10 -> movie.getOperator().getBirthday();
            case 11 -> movie.getOperator().getHeight();
            case 12 -> movie.getAuthor().getUsername();
            default -> "#";
        };
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames.get(columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0, 11, 7, 5, 3, 6 -> Integer.class;
            case 1, 12, 9 -> String.class;
            case 2 -> Float.class;
            case 4 -> LocalDate.class;
            case 8 -> MovieGenre.class;
            case 10 -> ZonedDateTime.class;
            default -> String.class;
        };
    }

    public void update() {
        setColumnNames(List.of(
                localizationService.get("movie.fields.id"),
                localizationService.get("movie.fields.name"),
                localizationService.get("movie.fields.coordx"),
                localizationService.get("movie.fields.coordy"),
                localizationService.get("movie.fields.cdate"),
                localizationService.get("movie.fields.oscars"),
                localizationService.get("movie.fields.gpalms"),
                localizationService.get("movie.fields.length"),
                localizationService.get("movie.fields.genre"),
                localizationService.get("movie.fields.opname"),
                localizationService.get("movie.fields.opbd"),
                localizationService.get("movie.fields.oph"),
                localizationService.get("movie.fields.author")
        ));
        fireTableStructureChanged();
    }


}
