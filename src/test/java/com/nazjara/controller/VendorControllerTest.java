package com.nazjara.controller;

import com.nazjara.model.Vendor;
import com.nazjara.repository.VendorRepository;
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
public class VendorControllerTest {

    WebTestClient webTestClient;

    @InjectMocks
    VendorController vendorController;

    @Mock
    VendorRepository vendorRepository;

    @Before
    public void setUp() {
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    public void list() {
        given(vendorRepository.findAll()).willReturn(Flux.just(Vendor.builder().name("test1").build(),
                Vendor.builder().name("test2").build()));

        webTestClient.get().uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    public void getById() {
        Vendor vendor = Vendor.builder().name("test1").build();

        given(vendorRepository.findById("1")).willReturn(Mono.just(vendor));

        webTestClient.get().uri("/api/v1/vendors/1")
                .exchange()
                .expectBody(Vendor.class)
                .isEqualTo(vendor);
    }
}