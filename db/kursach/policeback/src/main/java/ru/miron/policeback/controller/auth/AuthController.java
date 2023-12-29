package ru.miron.policeback.controller.auth;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.miron.policeback.controller.auth.model.request.LoginRequest;

@RestController
@RequestMapping("/api/v${api.version}")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationProvider authenticationProvider;

    @PostMapping("/public/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        Authentication authentication;
        try {
            authentication = authenticationProvider
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    loginRequest.getSeries(),
                                    loginRequest.getPassword()));
            if (authentication == null) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
