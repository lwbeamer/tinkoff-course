package ru.tinkoff.edu.java.scrapper.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.dto.GitHubResponse;

public class GitHubClient {


    @Value("${gh.baseurl}")
    private String gitHubBaseUrl;

    private final WebClient webClient;

    //для использования baseUrl по умолчанию (берётся из properties)
    public GitHubClient() {
        this.webClient = WebClient.create(gitHubBaseUrl);
    }


    //можно указать базовый URL
    public GitHubClient(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }


    public GitHubResponse fetchRepo(String owner, String repo) {
        GitHubResponse response = webClient.get().uri("/repos/{owner}/{repo}", owner, repo).retrieve()
                .bodyToMono(GitHubResponse.class).block();

        return response;

    }
}
