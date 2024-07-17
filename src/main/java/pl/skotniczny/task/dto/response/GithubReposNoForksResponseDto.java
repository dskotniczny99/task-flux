package pl.skotniczny.task.dto.response;

import java.util.List;

public record GithubReposNoForksResponseDto(String name, OwnerDto owner, List<GithubBranchesResponseDto> branches) {
}
