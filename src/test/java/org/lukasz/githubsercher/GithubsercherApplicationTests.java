package org.lukasz.githubsercher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.lukasz.githubsercher.controller.GithubController;
import org.lukasz.githubsercher.dto.RepositoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GithubsercherApplicationTests {

    @LocalServerPort
    private static int dynamicPort;
    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance().options(wireMockConfig().port(dynamicPort)).build();
    @Autowired
    WebTestClient webTestClient;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private GithubController githubController;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("baseUrl", wireMockServer::baseUrl);

    }

    @Test
    void testGetUserRepositoriesSuccessJson() {

        String username = "octocat";


        webTestClient.get().uri("/repositories/" + username).accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk().expectBody().json(Response.jsonData);

    }

    @Test
    void testGetRepositoriesWithWireMock() throws JsonProcessingException {


        List<RepositoryDto> repos = githubController.getGithubRepositories("octocat");

        List<RepositoryDto> expected = objectMapper.readValue(Response.jsonData, new TypeReference<>() {
        });
        assertThat(repos).isEqualTo(expected);


    }

}
