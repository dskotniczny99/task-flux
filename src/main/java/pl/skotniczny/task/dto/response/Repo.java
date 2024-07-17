package pl.skotniczny.task.dto.response;

import lombok.Builder;

@Builder
public record Repo(String login, String repoName, boolean fork) {
}
