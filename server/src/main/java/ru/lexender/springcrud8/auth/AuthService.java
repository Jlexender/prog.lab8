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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return new AuthResponse(false, "Successfully registered",
                jwtService.generateToken(userdataService.save(user)));
    }

    public AuthResponse authenticate(String username, String password) {
        log.info("Trying to authenticate by password");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        Userdata user = userdataService.findByUsername(username).orElseThrow();
        String token = jwtService.generateToken(user);

        return new AuthResponse(false, "Successfully logged in", token);
    }
}
