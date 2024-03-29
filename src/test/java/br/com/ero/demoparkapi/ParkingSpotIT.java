package br.com.ero.demoparkapi;

import br.com.ero.demoparkapi.web.dto.ClientCreateDto;
import br.com.ero.demoparkapi.web.dto.ParkingSpotCreateDto;
import br.com.ero.demoparkapi.web.exception.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/parkingSpot/parkingSpot-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/parkingSpot/parkingSpot-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ParkingSpotIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createParkingSpot_DataValid_ReturnLocationStatus201(){
        testClient
                .post()
                .uri("/api/v1/parkingSpot")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(new ParkingSpotCreateDto("A-05", "FREE"))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION);
    }

    @Test
    public void createParkingSpot_ExistingCode_ReturnErrorMessageStatus409(){
        testClient
                .post()
                .uri("/api/v1/parkingSpot")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(new ParkingSpotCreateDto("A-01", "FREE"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody()
                .jsonPath("status").isEqualTo(409)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/parkingSpot");
    }

    @Test
    public void createParkingSpot_DataInvalid_ReturnErrorMessageStatus422(){
        testClient
                .post()
                .uri("/api/v1/parkingSpot")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(new ParkingSpotCreateDto("", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/parkingSpot");
        testClient
                .post()
                .uri("/api/v1/parkingSpot")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(new ParkingSpotCreateDto("A-012", "LAL"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/v1/parkingSpot");
    }
    @Test
    public void createParkingSpot_UserNotAllowed_ReturnErrorMessageStatus403() {
         testClient
                 .post()
                 .uri("/api/v1/parkingSpot")
                 .contentType(MediaType.APPLICATION_JSON)
                 .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com", "123456"))
                 .bodyValue(new ParkingSpotCreateDto("A-05", "FREE"))
                 .exchange()
                 .expectStatus().isForbidden()
                 .expectBody()
                 .jsonPath("status").isEqualTo(403)
                 .jsonPath("method").isEqualTo("POST");

    }
    @Test
    public void getParkingSpot_ExistingCode_ReturnStatus200(){
        testClient
                .get()
                .uri("/api/v1/parkingSpot/{code}", "A-01")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("id").isEqualTo(10)
                .jsonPath("code").isEqualTo("A-01")
                .jsonPath("status").isEqualTo("FREE");
    }

    @Test
    public void getParkingSpot_NoneExistingCode_ReturnErrorMessageStatus404(){
        testClient
                .get()
                .uri("/api/v1/parkingSpot/{code}", "A-10")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo(404)
                .jsonPath("method").isEqualTo(  "GET")
                .jsonPath("path").isEqualTo("/api/v1/parkingSpot/A-10");
    }
    @Test
    public void getParkingSpot_UserNotAllowed_ReturnErrorMessageStatus403() {
        testClient
                .get()
                .uri("/api/v1/parkingSpot/{code}", "A-10")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo(403)
                .jsonPath("method").isEqualTo("GET")
                .jsonPath("path").isEqualTo("/api/v1/parkingSpot/A-10");

    }
}
