package ru.lexender.springcrud8.command;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lexender.springcrud8.auth.userdata.Userdata;
import ru.lexender.springcrud8.transfer.CommandRequest;
import ru.lexender.springcrud8.transfer.CommandResponse;

@RestController
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/")
public class CommandController {
    CommandHandler commandHandler;

    @PostMapping("/query")
    public ResponseEntity<CommandResponse> makeQuery(@RequestBody CommandRequest request,
                                                     @AuthenticationPrincipal Userdata userdata) {
        return ResponseEntity.ok(commandHandler.handle(request, userdata));
    }
}
