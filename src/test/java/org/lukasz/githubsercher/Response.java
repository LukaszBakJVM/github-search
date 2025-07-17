package org.lukasz.githubsercher;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.lukasz.githubsercher.dto.RepositoryDto;
import org.lukasz.githubsercher.model.Repository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class Response {
    Gson gson = new Gson();


    private final List<Repository> repositories = loadRepositories();
    String jsonData = gson.toJson(repositories);
    Type listType = new TypeToken<List<RepositoryDto>>() {
    }.getType();
    List<RepositoryDto> expected = gson.fromJson(this.jsonData, listType);

    private List<Repository> loadRepositories() {

        Resource resource = new ClassPathResource("response.json");
        try (InputStreamReader reader = new InputStreamReader(resource.getInputStream())) {
            Type listType = new TypeToken<List<Repository>>() {
            }.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}