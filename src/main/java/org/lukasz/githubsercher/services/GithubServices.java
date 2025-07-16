package org.lukasz.githubsercher.services;


import org.lukasz.githubsercher.dto.Mapper;
import org.lukasz.githubsercher.model.Branch;
import org.lukasz.githubsercher.model.Repository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.concurrent.ExecutorService;


@Service
public class GithubServices {


    private final RestClient restClient;
    private final ExecutorService executorService;
    private final Mapper mapper;

    public GithubServices(RestClient restClient, ExecutorService executorService, Mapper mapper) {
        this.restClient = restClient;
        this.executorService = executorService;
        this.mapper = mapper;
    }


    public List<Repository> getRepositories(String username) {


        List<Repository> repositories = restClient.get().uri("/users/{username}/repos", username).
                header("Accept", "application/json").retrieve().
                body((new ParameterizedTypeReference<>() {
                }));
        return repositories.stream()
                .map(repository -> (new Repository(repository.name(), repository.owner(),
                       ! repository.fork(),
                        this.getBranches(username, repository.name())))).toList();


    }

    List<Branch> getBranches(String username, String repositoryName) {
        return restClient.get().uri("/repos/{username}/{repositoryName}/branches", username, repositoryName).header("Accept", "application/json").retrieve().
                body((new ParameterizedTypeReference<>() {
                }));

    }
}


