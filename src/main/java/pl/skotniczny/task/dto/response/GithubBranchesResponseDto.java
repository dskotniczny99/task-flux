package pl.skotniczny.task.dto.response;

public record GithubBranchesResponseDto(String name, GithubCommitResponseDto commit) {
}
