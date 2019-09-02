package com.nazjara.bootstrap;

import com.nazjara.model.Category;
import com.nazjara.model.Vendor;
import com.nazjara.repository.CategoryRepository;
import com.nazjara.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    private VendorRepository vendorRepository;

    @Autowired
    public DataLoader(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) {
        if(categoryRepository.count().block() > 0) {
            return;
        }

        categoryRepository.save(Category.builder().description("Fruits").build()).block();
        categoryRepository.save(Category.builder().description("Dried").build()).block();
        categoryRepository.save(Category.builder().description("Fresh").build()).block();
        categoryRepository.save(Category.builder().description("Exotic").build()).block();
        categoryRepository.save(Category.builder().description("Nuts").build()).block();

        vendorRepository.save(Vendor.builder().name("Vendor1").build()).block();
        vendorRepository.save(Vendor.builder().name("Vendor2").build()).block();
    }
}
