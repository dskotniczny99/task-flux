package pl.skotniczny.task.github;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.skotniczny.task.dto.response.GithubAllReposResponseDto;
import pl.skotniczny.task.dto.response.GithubBranchesResponseDto;

import java.util.List;


@Component
public interface GithubWebFluxClient {

    @GetMapping("/users/{userName}/repos")
    List<GithubAllReposResponseDto> getAllUserRepos(@PathVariable("userName") String userName);

    @GetMapping("/repos/{owner}/{repo}/branches")
    List<GithubBranchesResponseDto> getBranches(@PathVariable("owner") String owner, @PathVariable("repo") String repo);
}
