package pl.skotniczny.task.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.skotniczny.task.dto.response.GithubAllReposResponseDto;
import pl.skotniczny.task.dto.response.GithubBranchesResponseDto;
import pl.skotniczny.task.dto.response.GithubReposNoForksResponseDto;
import pl.skotniczny.task.github.GithubFeignClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class GithubService {

    private final GithubFeignClient githubFeignClient;

    public List<GithubReposNoForksResponseDto> getAllRepos(String userName) {
        List<GithubAllReposResponseDto> allUserRepos = githubFeignClient.getAllUserRepos(userName);

        for (GithubAllReposResponseDto singleRepo : allUserRepos) {
            Repo repo = new Repo(singleRepo
                    .owner()
                    .login(),
                    singleRepo.name(), singleRepo.fork());

            log.info(String.valueOf(repo));
        }
        return allUserRepos.stream()
                .filter(repo -> !repo.fork())
                .map(repo -> {
                    List<GithubBranchesResponseDto> branches = githubFeignClient.getBranches(userName, repo.name());
                    return new GithubReposNoForksResponseDto(repo.name(), repo.owner(), branches);
                }).collect(Collectors.toList());
    }

}
