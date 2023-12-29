package ru.miron.policeback.entities;

import jakarta.persistence.*;

import java.util.Set;

import lombok.*;


@Entity
@EqualsAndHashCode(of = {"id"})
@Getter
@Setter
public class Crime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(mappedBy = "crime", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private CrimeCase crimeCase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_district")
    private District district;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_type")
    private CrimeType type;

    @OneToMany(mappedBy = "crime")
    private Set<PersonRelevantToCase> peopleCaseRelations;

    @OneToMany(mappedBy = "crime")
    private Set<PolicemanCase> policemenCaseAssignments;

    @OneToMany(mappedBy = "crime")
    private Set<Note> caseNotes;
}
