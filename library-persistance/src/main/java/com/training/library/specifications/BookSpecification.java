package com.training.library.specifications;

import com.training.library.entities.Author;
import com.training.library.entities.Book;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

public class BookSpecification {

    public static Specification<Book> containsTitle(String title) {
        return (root, query, builder) -> builder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Book> containsProperty(String key, Object value) {
        return (root, query, builder) -> builder.equal(root.get(key), value);
    }

    public static Specification<Book> containsAuthorName(String authorName) {
        return (root, query, builder) -> {
            final Join<Book, Author> authors = root.join("author", JoinType.INNER);
            return builder.like(authors.get("name"),  "%" + authorName + "%");
        };
    }

    public static Specification<Book> containsAuthorId(Integer id) {
        return (root, query, builder) -> {
            final Join<Book, Author> authors = root.join("author", JoinType.LEFT);
            return builder.equal(authors.get("id"), id);
        };
    }
}
