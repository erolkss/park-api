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

    @Test
    @Sql(scripts = "/sql/parking/parking-insert-parkingspot-busy.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/parking/parking-delete-parkingspot-busy.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void createCheckIn_ParkingSpotBusy_ReturnErrorStatus404() {
        ParkingCreateDto createDto = ParkingCreateDto.builder()
                .plate("WER-1111").brand("FIAT").model("PALIO 1.1").color("BLUE").clientCpf("95536891081")
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

    @Test
    public void getCheckIn_ProfileAdmin_ReturnDataStatus200() {
        testClient
                .get()
                .uri("/api/v1/parking/check-in/{receipt}", "20240118-205706")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("plate").isEqualTo("FIT-1020")
                .jsonPath("brand").isEqualTo("FIAT")
                .jsonPath("model").isEqualTo("PALIO")
                .jsonPath("color").isEqualTo("GREEN")
                .jsonPath("clientCpf").isEqualTo("95536891081")
                .jsonPath("receipt").isEqualTo("20240118-205706")
                .jsonPath("entryDate").isEqualTo("2024-01-18 20:57:06")
                .jsonPath("codeParkingSpot").isEqualTo("A-01");
    }

    @Test
    public void getCheckIn_ProfileClient_ReturnDataStatus200() {
        testClient
                .get()
                .uri("/api/v1/parking/check-in/{receipt}", "20240118-205706")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("plate").isEqualTo("FIT-1020")
                .jsonPath("brand").isEqualTo("FIAT")
                .jsonPath("model").isEqualTo("PALIO")
                .jsonPath("color").isEqualTo("GREEN")
                .jsonPath("clientCpf").isEqualTo("95536891081")
                .jsonPath("receipt").isEqualTo("20240118-205706")
                .jsonPath("entryDate").isEqualTo("2024-01-18 20:57:06")
                .jsonPath("codeParkingSpot").isEqualTo("A-01");
    }

    @Test
    public void getCheckIn_ReceiptNotExist_ReturnErrorStatus404() {
        testClient
                .get()
                .uri("/api/v1/parking/check-in/{receipt}", "20240118-999999")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo(404)
                .jsonPath("path").isEqualTo("/api/v1/parking/check-in/20240118-999999")
                .jsonPath("method").isEqualTo("GET");
    }

    @Test
    public void createCheckOut_ReceiptExist_ReturnStatus200() {
        testClient
                .put()
                .uri("/api/v1/parking/check-out/{receipt}", "20240118-205706")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("plate").isEqualTo("FIT-1020")
                .jsonPath("brand").isEqualTo("FIAT")
                .jsonPath("model").isEqualTo("PALIO")
                .jsonPath("color").isEqualTo("GREEN")
                .jsonPath("entryDate").isEqualTo("2024-01-18 20:57:06")
                .jsonPath("clientCpf").isEqualTo("95536891081")
                .jsonPath("codeParkingSpot").isEqualTo("A-01")
                .jsonPath("receipt").isEqualTo("20240118-205706")
                .jsonPath("exitDate").exists()
                .jsonPath("price").exists()
                .jsonPath("discount").exists()
        ;
    }

    @Test
    public void createCheckOut_ReceiptNotExist_ReturnErrorStatus404() {
        testClient
                .put()
                .uri("/api/v1/parking/check-out/{receipt}", "20240118-200006")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo(404)
                .jsonPath("path").isEqualTo("/api/v1/parking/check-out/20240118-200006")
                .jsonPath("method").isEqualTo("PUT");
        ;
    }


}
