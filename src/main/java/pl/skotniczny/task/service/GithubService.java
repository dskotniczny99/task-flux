package pl.skotniczny.task.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.skotniczny.task.dto.response.GithubReposResponseDto;
import pl.skotniczny.task.github.GithubFeignClient;

import java.util.List;

@Service
@AllArgsConstructor
public class GithubService {

    private final GithubFeignClient githubFeignClient;

    public List<GithubReposResponseDto> getAllRepos(String userName) {
        return githubFeignClient.getAllUserRepos(userName);
    }

}
