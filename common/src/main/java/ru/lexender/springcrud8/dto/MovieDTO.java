package ru.lexender.springcrud8.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MovieDTO {
    Long id;
    String name;
    CoordinatesDTO coordinates;
    LocalDate creationDate;
    Long oscarsCount;
    Integer goldenPalmCount;
    Integer length;
    MovieGenre genre;
    PersonDTO operator;
    UserdataDTO author;
}
