package ru.lexender.springcrud8.model.movie;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.lexender.springcrud8.auth.userdata.Userdata;
import ru.lexender.springcrud8.dto.MovieDTO;
import ru.lexender.springcrud8.model.util.ModelMapperService;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/db")
public class MovieController {
    MovieService movieService;
    ModelMapperService mapperService;

    @GetMapping("/find_all")
    public ResponseEntity<List<MovieDTO>> findAll() {
        return ResponseEntity.ok(movieService
                .findAll()
                .stream()
                .map(m -> mapperService.toDTO(m, MovieDTO.class))
                .toList());
    }

    @GetMapping("/find")
    public ResponseEntity<MovieDTO> findById(@RequestParam Long id) {
        return ResponseEntity.ok(mapperService.toDTO(movieService.findById(id), MovieDTO.class));
    }

    @PostMapping("/save")
    public void save(@RequestBody MovieDTO movieDTO,
                        @AuthenticationPrincipal Userdata user) {
        Movie movie = mapperService.fromDto(movieDTO, Movie.class);
        movie.setAuthor(user);
        movieService.save(movie);
    }
}
