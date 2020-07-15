package com.training.library.entities;

import com.training.library.enums.Currency;
import com.training.library.enums.Language;
import com.training.library.enums.State;
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

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private State state;

    @Column(name = "price")
    private Integer price;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "book", fetch = FetchType.LAZY)
    private MathDetails mathDetails;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "book", fetch = FetchType.LAZY)
    private HistoryDetails historyDetails;

}
