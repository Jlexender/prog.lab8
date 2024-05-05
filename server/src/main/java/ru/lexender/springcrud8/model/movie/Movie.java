package ru.lexender.springcrud8.model.movie;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import ru.lexender.springcrud8.auth.userdata.Userdata;
import ru.lexender.springcrud8.model.coordinates.Coordinates;
import ru.lexender.springcrud8.model.person.Person;

import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    @NotNull
    @NotBlank
    String name;

    @Embedded
    @NotNull
    Coordinates coordinates;

    @NotNull
    @CreationTimestamp
    LocalDate creationDate;

    @NotNull
    @Positive
    Long oscarsCount;

    @NotNull
    @Positive
    Integer goldenPalmCount;

    @NotNull
    @Positive
    Integer length;

    @NotNull
    @Enumerated(EnumType.STRING)
    MovieGenre genre;

    @NotNull
    @Embedded
    Person operator;

    @NotNull
    @ManyToOne
    Userdata author;
}
