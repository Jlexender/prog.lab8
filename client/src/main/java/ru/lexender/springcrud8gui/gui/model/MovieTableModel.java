package ru.lexender.springcrud8gui.gui.model;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.lexender.springcrud8.dto.MovieDTO;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Component
public class MovieTableModel extends AbstractTableModel {


    public void update() {
    }

    public List<MovieDTO> getMovieDtos() {
        return new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }
}