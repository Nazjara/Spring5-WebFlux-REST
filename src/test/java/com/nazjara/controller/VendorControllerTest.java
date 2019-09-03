package com.nazjara.controller;

import com.nazjara.model.Vendor;
import com.nazjara.repository.VendorRepository;
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
public class VendorControllerTest {

    WebTestClient webTestClient;
    Vendor vendor1;
    Vendor vendor2;

    @InjectMocks
    VendorController vendorController;

    @Mock
    VendorRepository vendorRepository;

    @Before
    public void setUp() {
        webTestClient = WebTestClient.bindToController(vendorController).build();

        vendor1 = Vendor.builder().name("test1").build();
        vendor2 = Vendor.builder().name("test2").build();
    }

    @Test
    public void testList() {
        given(vendorRepository.findAll()).willReturn(Flux.just(Vendor.builder().name("test1").build(),
                Vendor.builder().name("test2").build()));

        webTestClient.get().uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    public void testGetById() {
        Vendor vendor = Vendor.builder().name("test1").build();

        given(vendorRepository.findById("1")).willReturn(Mono.just(vendor));

        webTestClient.get().uri("/api/v1/vendors/1")
                .exchange()
                .expectBody(Vendor.class)
                .isEqualTo(vendor);
    }

    @Test
    public void testCreate() {
        given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(vendor1));

        webTestClient.post().uri("/api/v1/vendors")
                .body(Mono.just(vendor1), Vendor.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Void.class);
    }

    @Test
    public void testReplace() {
        given(vendorRepository.save(vendor1))
                .willReturn(Mono.just(vendor1));

        vendor1.setId("1");

        webTestClient.put().uri("/api/v1/vendors/1")
                .body(Mono.just(vendor1), Vendor.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Vendor.class)
                .isEqualTo(vendor1);
    }

    @Test
    public void testUpdate() {
        given(vendorRepository.save(vendor1)).willReturn(Mono.just(vendor1));
        given(vendorRepository.findById("1")).willReturn(Mono.just(vendor1));

        webTestClient.patch().uri("/api/v1/vendors/1")
                .body(Mono.just(vendor1), Vendor.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Vendor.class)
                .isEqualTo(vendor1);
    }
}