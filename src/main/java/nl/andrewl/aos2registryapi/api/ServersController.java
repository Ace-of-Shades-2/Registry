package nl.andrewl.aos2registryapi.api;

import nl.andrewl.aos2registryapi.ServerRegistry;
import nl.andrewl.aos2registryapi.dto.ServerInfoPayload;
import nl.andrewl.aos2registryapi.dto.ServerInfoResponse;
import nl.andrewl.aos2registryapi.dto.ServerShutdownPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/servers")
public class ServersController {
	private final ServerRegistry serverRegistry;

	public ServersController(ServerRegistry serverRegistry) {
		this.serverRegistry = serverRegistry;
	}

	@GetMapping
	public Flux<ServerInfoResponse> getServers() {
		return serverRegistry.getServers();
	}

	@PostMapping
	public Mono<ResponseEntity<Void>> updateServer(ServerHttpRequest req, @RequestBody Mono<ServerInfoPayload> payloadMono) {
		return serverRegistry.acceptInfo(req, payloadMono);
	}

	@PostMapping(path = "/shutdown")
	public Mono<ResponseEntity<Void>> shutdownServer(ServerHttpRequest req, @RequestBody Mono<ServerShutdownPayload> payloadMono) {
		return serverRegistry.onServerShutdown(req, payloadMono);
	}
}
