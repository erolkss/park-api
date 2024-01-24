package br.com.ero.demoparkapi;


import br.com.ero.demoparkapi.web.dto.ParkingCreateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/parking/parking-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/parking/parking-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ParkingIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createCheckIn_DataValid_ReturnCreatedAndLocation() {
        ParkingCreateDto createDto = ParkingCreateDto.builder()
                .plate("WER-1111").brand("FIAT").model("PALIO 1.0").color("BLUE").clientCpf("95536891081")
                .build();

        testClient
                .post()
                .uri("api/v1/parking/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION)
                .expectBody()
                .jsonPath("plate").isEqualTo("WER-1111")
                .jsonPath("brand").isEqualTo("FIAT")
                .jsonPath("model").isEqualTo("PALIO 1.0")
                .jsonPath("color").isEqualTo("BLUE")
                .jsonPath("clientCpf").isEqualTo("95536891081")
                .jsonPath("receipt").exists()
                .jsonPath("entryDate").exists()
                .jsonPath("codeParkingSpot").exists();

    }

    @Test
    public void createCheckIn_RoleClient_ReturnErrorStatus403() {
        ParkingCreateDto createDto = ParkingCreateDto.builder()
                .plate("WER-1111").brand("FIAT").model("PALIO 1.0").color("BLUE").clientCpf("95536891081")
                .build();

        testClient
                .post()
                .uri("api/v1/parking/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo(403)
                .jsonPath("path").isEqualTo("/api/v1/parking/check-in")
                .jsonPath("method").isEqualTo("POST")
        ;

    }

    @Test
    public void createCheckIn_DataInvalid_ReturnErrorStatus422() {
        ParkingCreateDto createDto = ParkingCreateDto.builder()
                .plate("").brand("").model("").color("").clientCpf("")
                .build();

        testClient
                .post()
                .uri("api/v1/parking/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422)
                .jsonPath("path").isEqualTo("/api/v1/parking/check-in")
                .jsonPath("method").isEqualTo("POST")
        ;

    }

    @Test
    public void createCheckIn_CpfNotExist_ReturnErrorStatus404() {
        ParkingCreateDto createDto = ParkingCreateDto.builder()
                .plate("WER-1111").brand("FIAT").model("PALIO 1.1").color("BLUE").clientCpf("10214759040")
                .build();

        testClient
                .post()
                .uri("/api/v1/parking/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo(404)
                .jsonPath("path").isEqualTo("/api/v1/parking/check-in")
                .jsonPath("method").isEqualTo("POST");

    }

}
