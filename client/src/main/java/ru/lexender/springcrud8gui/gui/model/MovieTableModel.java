package ru.lexender.springcrud8gui.gui.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import ru.lexender.springcrud8.dto.MovieDTO;
import ru.lexender.springcrud8.dto.MovieGenre;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class MovieTableModel extends AbstractTableModel {
    List<MovieDTO> movieDTOS = new ArrayList<>();
    List<String> columnNames;

    public MovieTableModel(List<String> columnNames) {
        this.columnNames = columnNames;
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
}
