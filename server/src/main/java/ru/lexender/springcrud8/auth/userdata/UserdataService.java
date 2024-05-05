package ru.lexender.springcrud8.auth.userdata;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Log4j2
@Service
@Transactional
public class UserdataService {
    UserdataRepository userdataRepository;

    public Optional<Userdata> findById(Integer id) {
        return userdataRepository.findById(id);
    }

    public Optional<Userdata> findByUsername(String username) {
        return userdataRepository.findByUsername(username);
    }

    public void deleteById(Integer id) {
        userdataRepository.deleteById(id);
    }

    public void deleteByUsername(String username) {
        userdataRepository.deleteByUsername(username);
    }

    public List<Userdata> findAll() {
        return userdataRepository.findAll();
    }

    public void deleteAll() {
        userdataRepository.deleteAll();
    }

    public boolean existsByUsername(String username) {
        return userdataRepository.existsByUsername(username);
    }

    public boolean existsById(Integer id) {
        return userdataRepository.existsById(id);
    }

    public Userdata save(Userdata userdata) {
        return userdataRepository.save(userdata);
    }

    public void replaceByUsername(String username, Userdata userdata) {
        Optional<Userdata> found = findByUsername(username);
        found.ifPresent(value -> userdata.setId(value.getId()));
        save(userdata);
    }
}
