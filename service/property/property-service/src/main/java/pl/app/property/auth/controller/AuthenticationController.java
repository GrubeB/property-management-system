package pl.app.property.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.security.authentication.AuthenticationService;
import pl.app.common.security.token.TokenService;
import pl.app.property.auth.command.AuthenticationCommand;

@RestController
@RequestMapping(AuthenticationController.controllerPath)
@RequiredArgsConstructor
public class AuthenticationController {
    public static final String controllerPath = "/api/v1/authenticate";
    private final AuthenticationService service;
    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationCommand dto) {
        Authentication authentication = service.authenticate(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok(tokenService.generateToken(authentication));
    }
}
