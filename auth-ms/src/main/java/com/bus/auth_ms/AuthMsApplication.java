package com.bus.auth_ms;

import com.bus.auth_ms.entity.Role;
import com.bus.auth_ms.repository.RoleRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class AuthMsApplication {

	private final RoleRepository roleRepository;

	// Constructor injection
	public AuthMsApplication(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthMsApplication.class, args);
	}

	// Pre-load roles into the database after bean initialization
	@PostConstruct
	public void initRoles() {
		if (!roleRepository.findByName("USER").isPresent()) {
			roleRepository.save(new Role("USER"));
		}
		if (!roleRepository.findByName("ADMIN").isPresent()) {
			roleRepository.save(new Role("ADMIN"));
		}
	}
}
