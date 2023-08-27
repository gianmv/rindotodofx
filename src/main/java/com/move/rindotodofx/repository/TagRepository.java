package com.move.rindotodofx.repository;

import com.move.rindotodofx.model.database.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findFirstByTagNameIgnoreCaseOrAbbreviationIgnoreCase(String tagName, String abbreviation);
    List<Tag> findByTagNameStartsWithIgnoreCaseOrAbbreviationStartsWithIgnoreCase(String tagName, String abbreviation);
}