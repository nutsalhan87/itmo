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
public class Policeman {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_person", unique = true, nullable = false)
    private Person asPerson;

    @Column(nullable = false)
    private String series;

    @Enumerated(EnumType.STRING)
    private Rank rank;

    @Column(nullable = false)
    private LocalDate dateOfIssue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_precinct", nullable = false)
    private Precinct precinct;

    @OneToMany(mappedBy = "policemanMajor", fetch = FetchType.LAZY)
    private Set<CrimeCase> policemanMajorCrimeCases;

    @OneToMany(mappedBy = "policeman", fetch = FetchType.LAZY)
    private Set<PersonRelevantToCase> policemanPersonRelevantToCases;

    @OneToMany(mappedBy = "major", fetch = FetchType.LAZY)
    private Set<PolicemanCase> assignmentsAsMajor;

    @OneToMany(mappedBy = "minor", fetch = FetchType.LAZY)
    private Set<PolicemanCase> assignmentsAsMinor;

    @OneToMany(mappedBy = "policemanOwner", fetch = FetchType.LAZY)
    private Set<Note> notesOwning;

    @OneToOne(mappedBy = "policeman", fetch = FetchType.LAZY)
    private Auth auth;

    public enum Rank {
        MAJOR,
        MINOR;
    }
}
