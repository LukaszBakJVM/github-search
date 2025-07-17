package org.lukasz.githubsercher.controller;

import org.lukasz.githubsercher.dto.RepositoryDto;
import org.lukasz.githubsercher.services.GithubServices;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/repositories")
public class GithubController {
    private final GithubServices githubServices;

    public GithubController(GithubServices githubServices) {
        this.githubServices = githubServices;
    }

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
   public List<RepositoryDto> getGithubRepositories(@PathVariable String username) {
        return githubServices.getRepositories(username);
    }
}
