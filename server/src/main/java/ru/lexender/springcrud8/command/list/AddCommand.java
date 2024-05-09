package ru.lexender.springcrud8.command.list;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.lexender.springcrud8.auth.userdata.Userdata;
import ru.lexender.springcrud8.command.AbstractCommand;
import ru.lexender.springcrud8.dto.MovieDTO;
import ru.lexender.springcrud8.model.movie.Movie;
import ru.lexender.springcrud8.model.movie.MovieService;
import ru.lexender.springcrud8.model.util.ModelMapperService;
import ru.lexender.springcrud8.transfer.CommandRequest;
import ru.lexender.springcrud8.transfer.CommandResponse;

import java.time.LocalDate;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class AddCommand extends AbstractCommand {
    MovieService movieService;
    ModelMapperService mapperService;

    public AddCommand(MovieService movieService, ModelMapperService mapperService) {
        super("add", "Adds objects to the collection",
                "[name] [coordinate x] [coordinate y] [oscars] " +
                        "[golden palms] [length] [genre] [operator name] " +
                        "[operator birthday] [operator height]", Userdata.Role.ROLE_USER);
        this.movieService = movieService;
        this.mapperService = mapperService;
    }

    @Override
    public CommandResponse execute(CommandRequest query, Userdata user) {
        List<MovieDTO> dtos = query.movies();
        List<Movie> movies = dtos.stream().map(m -> mapperService.fromDto(m, Movie.class)).toList();
        movies.forEach(m -> m.setCreationDate(LocalDate.now()));
        movies.forEach(m -> m.setAuthor(user));
        movieService.saveAll(movies);

        return CommandResponse
                .builder()
                .status(CommandResponse.Status.OK)
                .build();
    }
}
