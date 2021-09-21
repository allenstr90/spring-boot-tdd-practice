package aem.example.tdd.ecasastorage;

import aem.example.tdd.ecasastorage.config.EcasaProperties;
import aem.example.tdd.ecasastorage.entity.Authority;
import aem.example.tdd.ecasastorage.entity.User;
import aem.example.tdd.ecasastorage.repository.AuthorityRepository;
import aem.example.tdd.ecasastorage.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;

@SpringBootApplication
@EnableConfigurationProperties({EcasaProperties.class})
public class EcasaStorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcasaStorageApplication.class, args);
	}

	// TODO Use liquibase or something to initial load
	@Bean
	public CommandLineRunner commandLineRunner(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			authorityRepository.saveAll(Arrays.asList(new Authority("USER"), new Authority("ADMIN")));
			User user = new User();
			user.setActive(true);
			user.setUsername("admin");
			user.setPassword(passwordEncoder.encode("admin"));
			user.setEmail("admin@mail.com");
			user.setAuthorities(new HashSet<>(authorityRepository.findAll()));
			userRepository.save(user);
		};
	}

}
