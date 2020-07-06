package com.training.library.entities;

import com.training.library.enums.Language;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "BOOK")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "author_id")
    private Author author;

    @Column(name = "title")
    private String title;

    @Column(name = "editorial")
    private String editorial;

    @Column(name = "year")
    private Integer year;

    @Column(name = "pages")
    private Integer pages;

    @Column(name = "language")
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(name = "format")
    private String format;

    @Column(name = "isbn")
    private String isbn;

}
