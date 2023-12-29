package ru.miron.policeback.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Entity
@EqualsAndHashCode(of = {"crimeId", "personId"})
@Getter
@Setter
@IdClass(PersonRelevantToCase.Id.class)
public class PersonRelevantToCase {

    @jakarta.persistence.Id
    @Column(name = "fk_crime")
    private Integer crimeId;

    @jakarta.persistence.Id
    @Column(name = "fk_person")
    private Integer personId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "fk_crime")
    private Crime crime;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "fk_person")
    private Person person;

    @Enumerated(EnumType.STRING)
    private Relation relation;

    @Column(columnDefinition = "text")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_policeman", nullable = false)
    private Policeman policeman;

    public enum Relation {
        WITNESS,
        SUSPECT
    }

    @Data
    public static class Id implements Serializable {
        private Integer crimeId;
        private Integer personId;
    }
}
