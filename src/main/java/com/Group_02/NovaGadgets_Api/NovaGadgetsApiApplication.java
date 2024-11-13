package com.Group_02.NovaGadgets_Api;

import com.Group_02.NovaGadgets_Api.category.model.CategoryEntity;
import com.Group_02.NovaGadgets_Api.category.repository.CategoryRepository;
import com.Group_02.NovaGadgets_Api.factura.service.FacturaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class NovaGadgetsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NovaGadgetsApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner mappingDemo(
			CategoryRepository categoryRepository
	){
		return args ->{
			if(categoryRepository.count() == 0){
				categoryRepository.save(new CategoryEntity(0,"Componentes"));
				categoryRepository.save(new CategoryEntity(0,"Perifericos"));
				categoryRepository.save(new CategoryEntity(0,"Monitores"));
				categoryRepository.save(new CategoryEntity(0,"Laptops"));
				categoryRepository.save(new CategoryEntity(0,"PC completas"));
				categoryRepository.save(new CategoryEntity(0,"Adaptadores"));
			}
		};
	}
}
