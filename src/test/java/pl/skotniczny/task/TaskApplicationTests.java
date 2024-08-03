package pl.skotniczny.task;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@AutoConfigureWireMock(port = 0)
public class TaskApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void should_return_Repositories_for_user_and_status_200() {
        String userName = "dskotniczny99";
        webTestClient.get().uri(userName + "/repos")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json")
                .expectBody()
                .jsonPath("$[0].name").exists()
                .jsonPath("$[0].owner.login").isEqualTo(userName);
    }

    @Test
    public void should_return_not_existing_user_and_status_404() {
        String notExistingUser = "user123czxczci9fi";
        webTestClient.get()
                .uri(notExistingUser + "/repos")
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType("application/json")
                .expectBody()
                .jsonPath("$.status").isEqualTo("404")
                .jsonPath("$.message").isEqualTo("This user does not exist");
    }

    @Test
    public void should_return_not_acceptable_format_and_status_406() {
        String userName = "dskotniczny99";
        webTestClient.get().uri(userName + "/repos")
                .accept(MediaType.APPLICATION_XML)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_ACCEPTABLE)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.status").isEqualTo("406")
                .jsonPath("$.message").isEqualTo("Not acceptable format");
    }

    @Test
    public void should_return_status_200_and_empty_array() {
        String userWithoutRepos = "userWithoutRepos";
        webTestClient.get().uri("/test/repos/" + userWithoutRepos)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("[]");

    }

}

