package com.move.rindotodofx.repository;

import com.move.rindotodofx.model.database.Annotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface AnnotationRepository extends JpaRepository<Annotation, Long> {
}