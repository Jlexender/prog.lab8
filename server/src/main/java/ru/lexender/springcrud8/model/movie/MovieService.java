package ru.lexender.springcrud8.model.movie;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
@Transactional
public class MovieService {
    MovieRepository repository;

    public Optional<Movie> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<Movie> findAll() {
        return repository.findAll();
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public Movie save(Movie userdata) {
        return repository.save(userdata);
    }

    public List<Movie> saveAll(List<Movie> movies) {
        return repository.saveAll(movies);
    }

    public long count() {
        return repository.count();
    }
}
