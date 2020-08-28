package com.training.library.entities;

import com.training.library.enums.CurrencyEnum;
import com.training.library.enums.LanguageEnum;
import com.training.library.enums.StateEnum;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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
    private LanguageEnum language;

    @Column(name = "format")
    private String format;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private StateEnum state;

    @Column(name = "price")
    private Double price;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private CurrencyEnum currency;

    @Column(name = "active", columnDefinition = "TINYINT(1)")
    private Boolean active;

}
