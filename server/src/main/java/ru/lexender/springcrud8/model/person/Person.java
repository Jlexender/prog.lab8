package ru.lexender.springcrud8.model.person;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Getter
@Setter
@Builder
@ToString
public class Person {
    @NotNull
    @NotBlank
    @Column(name = "operator_name")
    String name;

    @NotNull
    @Column(name = "operator_birthday")
    ZonedDateTime birthday;

    @NotNull
    @Positive
    @Column(name = "operator_height")
    Double height;
}
