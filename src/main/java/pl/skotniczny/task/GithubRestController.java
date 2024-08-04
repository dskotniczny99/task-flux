package pl.skotniczny.task;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import pl.skotniczny.task.dto.response.GithubReposNoForksResponseDto;
import pl.skotniczny.task.service.GithubService;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class GithubRestController {

    private final GithubService githubService;

    @GetMapping("/{userName}/repos")
    public Mono<ResponseEntity<List<GithubReposNoForksResponseDto>>> getAllRepos(@PathVariable String userName, @RequestHeader(name = "Accept", defaultValue = "application/json") String header){


        return githubService.getAllRepos(userName)
                .map(repos -> ResponseEntity.ok().body(repos));

    }
}