package com.training.library.specifications;

import com.training.library.entities.Author;
import org.springframework.data.jpa.domain.Specification;

public class AuthorSpecification {

    public static Specification<Author> containsName(String name) {
        return (root, query, builder) -> builder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Author> containsProperty(String key, Object value) {
        return (root, query, builder) -> builder.equal(root.get(key), value);
    }

}
