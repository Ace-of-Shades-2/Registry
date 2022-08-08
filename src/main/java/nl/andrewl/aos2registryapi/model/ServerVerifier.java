package nl.andrewl.aos2registryapi.model;

import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * Interface for a component that can verify the authenticity of a server,
 * which is needed before we accept any information from a server.
 */
public interface ServerVerifier {
	boolean verify(ServerHttpRequest request) throws Exception;
}
