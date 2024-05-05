package ru.lexender.springcrud8.auth.userdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserdataRepository extends JpaRepository<Userdata, Integer> {
    Optional<Userdata> findByUsername(String username);
    void deleteByUsername(String username);
    boolean existsByUsername(String username);
}
