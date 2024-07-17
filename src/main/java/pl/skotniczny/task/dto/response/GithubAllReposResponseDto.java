package pl.skotniczny.task.dto.response;

import java.util.List;

public record GithubAllReposResponseDto(String name, OwnerDto owner, boolean fork, List<GithubBranchesResponseDto> branches) {
}
