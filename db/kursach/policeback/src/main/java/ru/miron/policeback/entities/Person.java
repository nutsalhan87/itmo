package ru.miron.policeback.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Entity
@EqualsAndHashCode(of = {"id"})
@Getter
@Setter
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(mappedBy = "asPerson", fetch = FetchType.LAZY)
    private Policeman asPoliceman;

    @Column
    private String name;

    @Column
    private LocalDate birthdate;

    @Enumerated(EnumType.STRING)
    private Race race;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private Set<PersonRelevantToCase> asRelevantToCase;

    public enum Race {
        SAMARAN,
        SEOLITE,
        OCCIDENTAL,
        KOJKO,
        MODIAL,
        PERIKARNASSIA,
        MESSINA,
        ORANJE,
        MESQUE,
        VESPER,
        SUR_LA_CLEF,
        UBI_SENT,
        GRAAD,
        KONIGSTEIN,
        ZSIEMSK,
        YUGO_GRAAD,
        IGAUNIJA,
        ZEMLYA,
        MIROVA,
        ILMARRAN,
        SEMENINE,
        REVACHOL,
        OZONNE,
        VAASA,
        SURU
    }
}
