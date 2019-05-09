package io.github.cepr0.demo;

import com.fasterxml.jackson.databind.Module;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.PageJacksonModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@EnableFeignClients
@RestController
@SpringBootApplication
public class Application {

	private final ModelRepo modelRepo;
	private final ModelClient modelClient;

	public Application(final ModelRepo modelRepo, final ModelClient modelClient) {
		this.modelRepo = modelRepo;
		this.modelClient = modelClient;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Module pageJacksonModule() {
		return new PageJacksonModule();
	}

	@GetMapping("/models")
	Page<Model> getAll(@RequestParam(value = "text", required = false, defaultValue = "text") String text, Pageable pageable) {
		return modelRepo.getAllByTextStartingWith(text, pageable);
	}

	@Order(Ordered.HIGHEST_PRECEDENCE)
	@EventListener
	public void populateDemoData(ApplicationReadyEvent e) {
		log.info("[i] Populating demo data...");
		modelRepo.saveAll(IntStream.rangeClosed(1, 100).mapToObj(Model::new).collect(Collectors.toList()));
	}

	@Order
	@EventListener
	public void requestData(ApplicationReadyEvent e) {
		log.info("[i] Requesting paged data...");
		Page<Model> models = modelClient.getAll("text", PageRequest.of(2, 10));
		if (models != null) models.forEach(System.out::println);
	}
}
