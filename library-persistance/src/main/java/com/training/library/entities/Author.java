package com.training.library.entities;

import com.training.library.enums.Language;
import com.training.library.enums.Nationality;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "nationality")
    @Enumerated(EnumType.STRING)
    private Nationality nationality;

    @Column(name = "native_language")
    @Enumerated(EnumType.STRING)
    private Language nativeLanguage;

}
