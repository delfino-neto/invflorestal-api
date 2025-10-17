package br.com.inv.florestal.api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import br.com.inv.florestal.api.models.user.Role;
import br.com.inv.florestal.api.repository.RoleRepository;

@SpringBootApplication
@EnableJpaAuditing
public class InvflorestalApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvflorestalApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(RoleRepository roleRepository){
		return args -> {
			if(roleRepository.findByName("USER").isEmpty()){
				roleRepository.save(Role.builder().name("USER").build());
			}
			if(roleRepository.findByName("ADMIN").isEmpty()){
				roleRepository.save(Role.builder().name("ADMIN").build());
			}
		};
	}
}
