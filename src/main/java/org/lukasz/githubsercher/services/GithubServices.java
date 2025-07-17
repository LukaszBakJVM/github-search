package org.lukasz.githubsercher.services;


import lombok.SneakyThrows;
import org.lukasz.githubsercher.dto.Mapper;
import org.lukasz.githubsercher.dto.RepositoryDto;
import org.lukasz.githubsercher.exceptions.FutureException;
import org.lukasz.githubsercher.exceptions.UserNotFoundException;
import org.lukasz.githubsercher.model.Branch;
import org.lukasz.githubsercher.model.Repository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.ExecutionException;
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


    @SneakyThrows
    public List<RepositoryDto> getRepositories(String username) {


        List<Repository> repositories = restClient.get().uri("/users/{username}/repos", username).accept(MediaType.APPLICATION_JSON).retrieve().onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
            if (response.getStatusCode().value() == 404) {
                throw new UserNotFoundException(String.format("User %s not found", username));
            }
            throw new ResponseStatusException(response.getStatusCode());
        })).body((new ParameterizedTypeReference<>() {
        }));
        return repositories.stream().filter(r -> !r.fork()).map(repo -> executorService.submit(() -> {
            List<Branch> branches = getBranches(username, repo.name());
            return mapper.fromRepositoryToDto(new Repository(repo.name(), repo.owner(), repo.fork(), branches));
        })).toList().stream().map(future -> {
            try {
                return future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new FutureException("Services unavailable");
            }
        }).toList();


    }

  private   List<Branch> getBranches(String username, String repositoryName) {
        return restClient.get().uri("/repos/{username}/{repositoryName}/branches", username, repositoryName).accept(MediaType.APPLICATION_JSON).retrieve().body((new ParameterizedTypeReference<>() {
        }));

    }
}


