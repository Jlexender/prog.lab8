package ru.lexender.springcrud8.auth;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.lexender.springcrud8.auth.userdata.Userdata;
import ru.lexender.springcrud8.auth.userdata.UserdataService;
import ru.lexender.springcrud8.transfer.AuthResponse;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RestController
public class AuthController {
    AuthService authService;
    UserdataService userdataService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestParam String username,
            @RequestParam String password) {

        if (userdataService.existsByUsername(username)) {
            return ResponseEntity
                    .status(401)
                    .body(new AuthResponse(true, "User with this username already exists", null));
        }
        if (password.isBlank()) {
            return ResponseEntity
                    .status(401)
                    .body(new AuthResponse(true, "Invalid username/password", null));

        }

        try {
            return ResponseEntity.ok(authService.register(Userdata
                    .builder().username(username).password(password).build()));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(new AuthResponse(true, ex.getMessage(), null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestParam String username,
            @RequestParam String password) {
        if (!userdataService.existsByUsername(username))
            return ResponseEntity
                    .status(401)
                    .body(new AuthResponse(true,  "Incorrect username/password", null));

        try {
            return ResponseEntity.ok(authService.authenticate(username, password));
        } catch (Exception ex) {
            return ResponseEntity.ok(new AuthResponse(true, "Invalid username/password", null));
        }
    }
}
