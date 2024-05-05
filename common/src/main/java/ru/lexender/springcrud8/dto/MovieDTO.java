package ru.lexender.springcrud8.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MovieDTO {
    String name;
    CoordinatesDTO coordinates;
    LocalDate creationDate;
    Long oscarsCount;
    Integer goldenPalmCount;
    Integer length;
    MovieGenre genre;
    PersonDTO operator;
}
