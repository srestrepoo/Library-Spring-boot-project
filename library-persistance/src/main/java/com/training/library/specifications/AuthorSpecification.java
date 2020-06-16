package com.training.library.specifications;

import com.training.library.entities.Author;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Data
public class AuthorSpecification implements Specification<Author> {

    private String authorKey;
    private String authorName;

    public AuthorSpecification(String authorKey, String authorName) {
        this.authorKey = authorKey;
        this.authorName = authorName;
    }

    @Override
    public Predicate toPredicate(Root<Author> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(
                root.get("name"),"%" + authorName + "%");
    }
}
