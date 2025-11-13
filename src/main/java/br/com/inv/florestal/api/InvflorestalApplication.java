package br.com.inv.florestal.api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import br.com.inv.florestal.api.models.user.Role;
import br.com.inv.florestal.api.repository.RoleRepository;
import br.com.inv.florestal.api.storage.StorageProperties;
import br.com.inv.florestal.api.storage.StorageService;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties(StorageProperties.class)
public class InvflorestalApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvflorestalApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(StorageService storageService, RoleRepository roleRepository) {
		return args -> {
			// Initialize storage
			storageService.init();
			
			// Initialize roles
			if(roleRepository.findByName("USER").isEmpty()){
				roleRepository.save(Role.builder().name("USER").build());
			}
			if(roleRepository.findByName("ADMIN").isEmpty()){
				roleRepository.save(Role.builder().name("ADMIN").build());
			}
		};
	}
}
