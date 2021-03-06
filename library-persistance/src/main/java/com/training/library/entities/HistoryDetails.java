package com.training.library.entities;

import com.training.library.enums.NationalityEnum;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "History_Details")
public class HistoryDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "historical_period")
    private String historicalPeriod;

    @Column(name = "country")
    @Enumerated(EnumType.STRING)
    private NationalityEnum country;

    @Column(name = "censure")
    private String censure;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "book_id")
    @MapsId
    private Book book;

}
