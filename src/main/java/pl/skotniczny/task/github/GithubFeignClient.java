package pl.skotniczny.task.github;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.skotniczny.task.dto.response.GithubReposResponseDto;
import java.util.List;

@Component
@FeignClient(name = "github-client",  url = "https://api.github.com")
public interface GithubFeignClient {

    // https://api.github.com/users/dskotniczny99/repos
    // localhost:8080/dskotniczny99/repos
    @GetMapping("/users/{userName}/repos")
    List<GithubReposResponseDto> getAllUserRepos(@PathVariable("userName") String userName);
}
