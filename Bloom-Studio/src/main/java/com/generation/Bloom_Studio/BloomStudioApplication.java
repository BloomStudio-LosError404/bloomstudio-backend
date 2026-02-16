package com.generation.Bloom_Studio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;

import com.generation.Bloom_Studio.entity.Rol;

@SpringBootApplication
public class BloomStudioApplication {

    public static void main(String[] args) {
        SpringApplication.run(BloomStudioApplication.class, args);
    }

    @Bean
    CommandLineRunner testRol(RolRepository rolRepository) {
        return args -> {
            // Verifica si ya existe el rol para no duplicarlo
            if (rolRepository.count() == 0) {
                Rol rol = new Rol("ADMIN");
                rolRepository.save(rol);
                System.out.println("Rol guardado en la base de datos");
            } else {
                System.out.println("La tabla roles ya contiene registros");
            }
        };
    }
}
