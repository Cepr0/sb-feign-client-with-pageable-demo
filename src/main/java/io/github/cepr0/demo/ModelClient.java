package io.github.cepr0.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "model-service", url = "http://localhost:8080/")
public interface ModelClient {
	@GetMapping("/models")
	Page<Model> getAll(@RequestParam(value = "text", required = false) String text, Pageable page);
}
