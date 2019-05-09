package io.github.cepr0.demo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ModelRepo extends JpaRepository<Model, Integer> {
	Page<Model> getAllByTextStartingWith(String text, Pageable pageable);
}
