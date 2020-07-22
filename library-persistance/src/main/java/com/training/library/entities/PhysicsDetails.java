package com.training.library.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Physics_Details")
public class PhysicsDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "subcategory")
    private String subcategory;

    @Column(name = "exercise")
    private String exercise;

    @Column(name = "answer")
    private String answer;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "book_id")
    @MapsId
    private Book book;

}
