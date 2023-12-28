package br.com.ero.demoparkapi.web.controller;

import br.com.ero.demoparkapi.jwt.JwtToken;
import br.com.ero.demoparkapi.jwt.JwtUserDetailsService;
import br.com.ero.demoparkapi.web.dto.UserLoginDto;
import br.com.ero.demoparkapi.web.exception.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.netty.http.server.HttpServer;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {
    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody @Valid UserLoginDto userLoginDto, HttpServletRequest servletRequest) {
        log.info("Login authentication process '{}'", userLoginDto.getUsername());
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(), userLoginDto.getPassword());
            authenticationManager.authenticate(authenticationToken);
            JwtToken jwtToken = detailsService.getTokenAuthenticated(userLoginDto.getUsername());
            return ResponseEntity.ok(jwtToken);
        } catch (AuthenticationException ex) {
            log.warn("Bad credentials from username'{}'", userLoginDto.getUsername());
        }
        return ResponseEntity
                .badRequest()
                .body(new ErrorMessage(servletRequest, HttpStatus.BAD_REQUEST, "Invalid Credentials"));
    }
}
