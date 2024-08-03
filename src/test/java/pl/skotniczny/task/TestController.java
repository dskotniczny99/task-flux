package pl.skotniczny.task;

import org.springframework.web.bind.annotation.*;
import pl.skotniczny.task.dto.response.GithubReposNoForksResponseDto;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/repos/{userName}")
    public List<GithubReposNoForksResponseDto> getEmptyRepos(@PathVariable String userName){
        return Collections.emptyList();
    }
}
