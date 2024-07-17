package pl.skotniczny.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.skotniczny.task.dto.response.GithubReposNoForksResponseDto;
import pl.skotniczny.task.dto.response.OwnerDto;
import pl.skotniczny.task.service.GithubService;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private GithubService githubService;

    @Autowired
    private ObjectMapper objectMapper;

    private final String userName = "dskotniczny99";

    private ResultActions performGetRequest(String userName) throws Exception {
        return mockMvc.perform(get("/" + userName + "/repos")
                .header("Accept", "application/json")
                .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void test_All_Repos_From_File() throws Exception {
        String expectedResponseJson = new String(Files.readAllBytes(Paths.get("src/test/resources/expectedResponse.json")));
        List<GithubReposNoForksResponseDto> expectedResponse = objectMapper.readValue(expectedResponseJson, objectMapper.getTypeFactory().constructCollectionType(List.class, GithubReposNoForksResponseDto.class));

        when(githubService.getAllRepos(userName)).thenReturn(expectedResponse);

        performGetRequest(userName)
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseJson));
    }

    @Test
    public void test_repo_name_and_owner_login() throws Exception {
        OwnerDto ownerDto = new OwnerDto(userName);
        GithubReposNoForksResponseDto responseDto = new GithubReposNoForksResponseDto("repoName", ownerDto, Collections.emptyList());
        List<GithubReposNoForksResponseDto> mockResponse = Collections.singletonList(responseDto);

        when(githubService.getAllRepos(userName)).thenReturn(mockResponse);

        performGetRequest(userName)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("blog"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].owner.login").value(userName));
    }

    @Test
    public void test_not_existing_user() throws Exception {
        String invalidUserName = "@@@@NOT_EXISTING_USER_GITHUB@@@@";

        when(githubService.getAllRepos(invalidUserName)).thenThrow(FeignException.NotFound.class);

        performGetRequest(invalidUserName)
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("This user does not exist"));
    }

    @Test
    public void test_not_acceptable_format() throws Exception {
        OwnerDto ownerDto = new OwnerDto(userName);
        GithubReposNoForksResponseDto responseDto = new GithubReposNoForksResponseDto("repoName", ownerDto, Collections.emptyList());
        List<GithubReposNoForksResponseDto> mockResponse = Collections.singletonList(responseDto);

        when(githubService.getAllRepos(userName)).thenReturn(mockResponse);

        mockMvc.perform(get("/" + userName + "/repos")
                        .header("Accept", "application/xml")
                        .contentType(MediaType.APPLICATION_XML))
                .andExpect(status().isNotAcceptable())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Not acceptable format"));
    }


}
