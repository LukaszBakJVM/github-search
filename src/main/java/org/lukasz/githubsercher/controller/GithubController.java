package org.lukasz.githubsercher.controller;

import org.lukasz.githubsercher.dto.RepositoryDto;
import org.lukasz.githubsercher.services.GithubServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/repositories")
public class GithubController {
    private final GithubServices githubServices;

    public GithubController(GithubServices githubServices) {
        this.githubServices = githubServices;
    }

    @GetMapping("/{username}")
    List<RepositoryDto> getGithubRepositories(@PathVariable String username) {
        return githubServices.getRepositories(username);
    }
}
