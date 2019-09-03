package com.nazjara.controller;

import com.nazjara.model.Category;
import com.nazjara.repository.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class CategoryControllerTest {

    WebTestClient webTestClient;

    @InjectMocks
    CategoryController categoryController;

    @Mock
    CategoryRepository categoryRepository;

    @Before
    public void setUp() {
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    public void list() {
        given(categoryRepository.findAll()).willReturn(Flux.just(Category.builder().description("test1").build(),
                Category.builder().description("test2").build()));

        webTestClient.get().uri("/api/v1/categories")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    public void getById() {
        Category category = Category.builder().description("test1").build();

        given(categoryRepository.findById("1")).willReturn(Mono.just(category));

        webTestClient.get().uri("/api/v1/categories/1")
                .exchange()
                .expectBody(Category.class)
                .isEqualTo(category);
    }
}