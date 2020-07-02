package com.training.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.training.library.enums.Language;
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
    private String nationality;

    @Column(name = "native_language")
    private String nativeLanguage;

}
