package ru.lexender.springcrud8.auth.refresh;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.lexender.springcrud8.auth.userdata.Userdata;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RefreshService {
    RefreshRepository repository;

    public RefreshToken save(RefreshToken token) {
        return repository.save(token);
    }

    public Optional<RefreshToken> findByUser(Userdata user) {
        return repository.findById(user.getUsername());
    }

    public Optional<RefreshToken> findByUsername(String username) {
        return repository.findById(username);
    }

    public boolean validate(String username, String token) {
        return findByUsername(username).orElseThrow().getToken().equals(token);
    }
}
