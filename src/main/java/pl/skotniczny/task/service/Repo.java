package pl.skotniczny.task.service;

import lombok.Builder;

@Builder
public record Repo(String login, String repoName, boolean fork) {
}
