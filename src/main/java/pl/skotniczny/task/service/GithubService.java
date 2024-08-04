package pl.skotniczny.task.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import pl.skotniczny.task.dto.response.GithubAllReposResponseDto;
import pl.skotniczny.task.dto.response.GithubBranchesResponseDto;
import pl.skotniczny.task.dto.response.GithubReposNoForksResponseDto;
import pl.skotniczny.task.error.UserNotFoundException;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class GithubService {

    private final WebClient.Builder webClientBuilder;


    public GithubService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<List<GithubReposNoForksResponseDto>> getAllRepos(String userName) {
        WebClient webClient = webClientBuilder
                .baseUrl("https://api.github.com")
                .build();

        return webClient.get()
                .uri("/users/{userName}/repos", userName)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new UserNotFoundException("This user does not exist"))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error"))
                )
                .bodyToFlux(GithubAllReposResponseDto.class)
                .filter(repo -> !repo.fork())
                .flatMap(repo -> webClient.get()
                        .uri("/repos/{owner}/{repo}/branches", repo.owner().login(), repo.name())
                        .retrieve()
                        .bodyToFlux(GithubBranchesResponseDto.class)
                        .collectList()
                        .map(branches -> new GithubReposNoForksResponseDto(repo.name(), repo.owner(), branches))
                )
                .collectList()
                .doOnError(WebClientResponseException.class, ex -> log.error("WebClient error: ", ex));
    }
}