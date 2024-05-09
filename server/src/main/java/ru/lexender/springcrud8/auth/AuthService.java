package ru.lexender.springcrud8.auth;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.lexender.springcrud8.auth.jwt.JwtService;
import ru.lexender.springcrud8.auth.userdata.Userdata;
import ru.lexender.springcrud8.auth.userdata.UserdataService;
import ru.lexender.springcrud8.transfer.AuthResponse;

import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Log4j2
@Service
public class AuthService {
    UserdataService userdataService;
    PasswordEncoder passwordEncoder;
    AuthenticationManager authenticationManager;
    JwtService jwtService;

    public AuthResponse register(Userdata user) {
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRT(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRefreshToken(passwordEncoder.encode(refreshToken));

        userdataService.save(user);
        return new AuthResponse(false, "Successfully registered",
                accessToken, refreshToken);
    }

    public AuthResponse authenticate(String username, String password) {
        log.info("Trying to authenticate by password");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        Userdata user = userdataService.findByUsername(username).orElseThrow();
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRT(user.getUsername());

        user.setRefreshToken(passwordEncoder.encode(refreshToken));
        userdataService.save(user);
        return new AuthResponse(false, "Successfully logged in",
                accessToken, refreshToken);
    }

    public AuthResponse refresh(String username, String token) {
        log.info("Trying to refresh by RT");

        Optional<Userdata> foundUser = userdataService.findByUsername(username);
        if (foundUser.isEmpty()) {
            return new AuthResponse(true, "DENIED", null, null);
        }
        Userdata user = foundUser.get();

        if (passwordEncoder.matches(token, user.getRefreshToken())) {
            String accessToken = jwtService.generateToken(user);
            String refreshToken = jwtService.generateRT(user.getUsername());
            user.setRefreshToken(refreshToken);
            userdataService.save(user);
            return new AuthResponse(false, "GRANTED", accessToken, refreshToken);
        }
        return new AuthResponse(true, "DENIED", null, null);
    }
}
