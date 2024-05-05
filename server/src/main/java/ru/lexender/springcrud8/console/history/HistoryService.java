package ru.lexender.springcrud8.console.history;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class HistoryService {
    HistoryRepository repository;

    public Optional<HistoryLog> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<HistoryLog> findAll() {
        return repository.findAll();
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public HistoryLog save(HistoryLog log) {
        return repository.save(log);
    }
}
