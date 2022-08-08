package nl.andrewl.aos2registryapi.model;

import org.springframework.http.server.reactive.ServerHttpRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class TokenFileServerVerifier implements ServerVerifier {
	private final Set<String> validTokens = new HashSet<>();

	public void loadFromFile(Path file) {
		if (!Files.isReadable(file)) {
			System.err.println("Cannot read tokens from file: " + file.toAbsolutePath());
			return;
		}
		try {
			validTokens.addAll(Files.readAllLines(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean verify(ServerHttpRequest request) {
		String token = request.getHeaders().getFirst("X-AOS2-REGISTRY-TOKEN");
		return token != null && validTokens.contains(token);
	}
}
