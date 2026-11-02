package com.example.app;

import java.util.Map;
import org.springframework.boot.SpringBootVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** A JSON endpoint that shows the runtime versions without touching the database. */
@RestController
public class StatusController {

	@GetMapping("/api/status")
	public Map<String, String> status() {
		return Map.of(
				"status", "ok",
				"java", Runtime.version().toString(),
				"springBoot", SpringBootVersion.getVersion());
	}

}
