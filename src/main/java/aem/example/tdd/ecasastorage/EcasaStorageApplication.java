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

}
