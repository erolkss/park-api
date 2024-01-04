package br.com.ero.demoparkapi;

import br.com.ero.demoparkapi.jwt.JwtToken;
import br.com.ero.demoparkapi.web.dto.UserLoginDto;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.function.Consumer;

public class JwtAuthentication {

    public static Consumer<HttpHeaders> getHeaderAuthorization(WebTestClient client, String username, String password) {
        String token = client
                .post()
                .uri("api/v1/auth")
                .bodyValue(new UserLoginDto(username, password))
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtToken.class)
                .returnResult().getResponseBody().getToken();
        return header -> header.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }
}
