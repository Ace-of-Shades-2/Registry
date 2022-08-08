package nl.andrewl.aos2registryapi;

import nl.andrewl.aos2registryapi.model.ServerVerifier;
import nl.andrewl.aos2registryapi.model.TokenFileServerVerifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.nio.file.Path;

@SpringBootApplication
@EnableScheduling
public class Aos2RegistryApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(Aos2RegistryApiApplication.class, args);
	}

	@Bean
	public ServerVerifier serverVerifier() {
		TokenFileServerVerifier verifier = new TokenFileServerVerifier();
		verifier.loadFromFile(Path.of("tokens.txt"));
		return verifier;
	}
}
