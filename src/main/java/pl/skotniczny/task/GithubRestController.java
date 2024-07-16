package pl.skotniczny.task;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.skotniczny.task.dto.response.GithubReposResponseDto;
import pl.skotniczny.task.service.GithubService;

import java.util.List;

@RestController
@AllArgsConstructor
public class GithubRestController {

    private final GithubService githubService;

    @GetMapping("/{userName}/repos")
    public ResponseEntity<List<GithubReposResponseDto>> getAllRepos(@PathVariable String userName) {
        List<GithubReposResponseDto> allRepos = githubService.getAllRepos(userName);
        return ResponseEntity.ok(allRepos);
    }



}
