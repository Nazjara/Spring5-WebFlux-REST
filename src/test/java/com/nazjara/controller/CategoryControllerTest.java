package com.nazjara.controller;

import com.nazjara.model.Category;
import com.nazjara.repository.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class CategoryControllerTest {

    WebTestClient webTestClient;
    Category category1;
    Category category2;

    @InjectMocks
    CategoryController categoryController;

    @Mock
    CategoryRepository categoryRepository;

    @Before
    public void setUp() {
        webTestClient = WebTestClient.bindToController(categoryController).build();

        category1 = Category.builder().description("test1").build();
        category2 = Category.builder().description("test2").build();
    }

    @Test
    public void testList() {
        given(categoryRepository.findAll()).willReturn(Flux.just(category1,
                category2));

        webTestClient.get().uri("/api/v1/categories")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    public void testGetById() {
        given(categoryRepository.findById("1")).willReturn(Mono.just(category1));

        webTestClient.get().uri("/api/v1/categories/1")
                .exchange()
                .expectBody(Category.class)
                .isEqualTo(category1);
    }

    @Test
    public void testCreate() {
        given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(category1));

        webTestClient.post().uri("/api/v1/categories")
                .body(Mono.just(category1), Category.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Void.class);
    }

    @Test
    public void testReplace() {
        given(categoryRepository.save(category1))
                .willReturn(Mono.just(category1));

        category1.setId("1");

        webTestClient.put().uri("/api/v1/categories/1")
                .body(Mono.just(category1), Category.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Category.class)
                .isEqualTo(category1);
    }

    @Test
    public void testUpdate() {
        given(categoryRepository.save(category1)).willReturn(Mono.just(category1));
        given(categoryRepository.findById("1")).willReturn(Mono.just(category1));

        webTestClient.patch().uri("/api/v1/categories/1")
                .body(Mono.just(category1), Category.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Category.class)
                .isEqualTo(category1);
    }
}