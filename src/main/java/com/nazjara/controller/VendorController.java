package com.nazjara.controller;

import com.nazjara.model.Vendor;
import com.nazjara.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class VendorController {

    private final VendorRepository vendorRepository;

    @Autowired
    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping("/api/v1/vendors")
    public Flux<Vendor> list() {
        return vendorRepository.findAll();
    }

    @GetMapping("/api/v1/vendors/{id}")
    public Mono<Vendor> getById(@PathVariable String id) {
        return vendorRepository.findById(id);
    }
}