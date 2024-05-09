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
public class UserdataService {
    UserdataRepository userdataRepository;

    @Transactional(readOnly = true)
    public Optional<Userdata> findById(Integer id) {
        return userdataRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Userdata> findByUsername(String username) {
        return userdataRepository.findByUsername(username);
    }

    @Transactional
    public void deleteById(Integer id) {
        userdataRepository.deleteById(id);
    }

    @Transactional
    public void deleteByUsername(String username) {
        userdataRepository.deleteByUsername(username);
    }

    @Transactional(readOnly = true)
    public List<Userdata> findAll() {
        return userdataRepository.findAll();
    }

    @Transactional
    public void deleteAll() {
        userdataRepository.deleteAll();
    }

    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userdataRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return userdataRepository.existsById(id);
    }

    @Transactional
    public Userdata save(Userdata userdata) {
        return userdataRepository.save(userdata);
    }

    @Transactional
    public void replaceByUsername(String username, Userdata userdata) {
        Optional<Userdata> found = findByUsername(username);
        found.ifPresent(value -> userdata.setId(value.getId()));
        save(userdata);
    }
}
