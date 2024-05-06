package ru.lexender.springcrud8gui.gui.model;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Configuration
public class TableModelConfiguration {

    @Bean
    public MovieTableModel movieTableModel() {
        return new MovieTableModel(List.of(
                "id",
                "name",
                "coordinate x",
                "coordinate y",
                "creation date",
                "oscars",
                "golden palms",
                "length",
                "genre",
                "operator name",
                "operator birthday",
                "operator height",
                "object author"
        ));
    }
}
