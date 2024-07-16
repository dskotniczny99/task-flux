package pl.skotniczny.task.dto.response;

public record GithubReposResponseDto(String name, Owner owner, boolean fork) {
}
