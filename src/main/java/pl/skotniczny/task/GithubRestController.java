package pl.skotniczny.task;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import pl.skotniczny.task.dto.response.GithubReposNoForksResponseDto;
import pl.skotniczny.task.service.GithubService;

import java.util.List;

@RestController
@AllArgsConstructor
public class GithubRestController {

    private final GithubService githubService;

    @GetMapping("/{userName}/repos")
    public ResponseEntity<List<GithubReposNoForksResponseDto>> getAllRepos(@PathVariable String userName, @RequestHeader(name = "Accept", defaultValue = "application/json") String header) {
        List<GithubReposNoForksResponseDto> allRepos = githubService.getAllRepos(userName);
        return ResponseEntity.ok(allRepos);
    }


}
