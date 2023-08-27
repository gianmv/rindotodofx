package com.move.rindotodofx.model.database;

import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "annotation")
public class Annotation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "annotation_id", nullable = false)
    private Long annotationId;

    @Column(name = "md_text", length = 10000)
    private String mdText;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "modification_date")
    private LocalDateTime modificationDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "annotation_tags",
            joinColumns = @JoinColumn(name = "annotation_annotation_id"),
            inverseJoinColumns = @JoinColumn(name = "tags_tag_id"))
    private Set<Tag> tags = new LinkedHashSet<>();

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "priority")
    private Integer priority;

    public Annotation() {
    }

    public Annotation(Annotation annotation) {
        this.annotationId = annotation.annotationId;
        this.mdText = annotation.mdText;
        this.creationDate = annotation.creationDate;
        this.modificationDate = annotation.modificationDate;
        this.tags = new LinkedHashSet<>(annotation.tags);
        this.dueDate = annotation.dueDate;
        this.priority = annotation.priority;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getMdText() {
        return mdText;
    }

    public void setMdText(String mdText) {
        this.mdText = mdText;
    }

    public Long getAnnotationId() {
        return annotationId;
    }

    public void setAnnotationId(Long annotationId) {
        this.annotationId = annotationId;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Annotation that = (Annotation) o;
        return getAnnotationId() != null && Objects.equals(getAnnotationId(), that.getAnnotationId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}