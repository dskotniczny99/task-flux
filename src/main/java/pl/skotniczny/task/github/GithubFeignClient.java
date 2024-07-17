package pl.skotniczny.task.github;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.skotniczny.task.dto.response.GithubBranchesResponseDto;
import pl.skotniczny.task.dto.response.GithubAllReposResponseDto;

import java.util.List;


@Component
@FeignClient(name = "github-client", url = "https://api.github.com")
public interface GithubFeignClient {

    @GetMapping("/users/{userName}/repos")
    List<GithubAllReposResponseDto> getAllUserRepos(@PathVariable("userName") String userName);

    @GetMapping("/repos/{owner}/{repo}/branches")
    List<GithubBranchesResponseDto> getBranches(@PathVariable("owner") String owner, @PathVariable("repo") String repo);
}
