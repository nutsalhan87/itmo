package ru.miron.policeback.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@EqualsAndHashCode(of = {"crimeId"})
@Getter
@Setter
public class CrimeCase {

    @Id
    @Column(name = "fk_crime")
    private Integer crimeId;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "fk_crime")
    private Crime crime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_policeman_major", nullable = false)
    private Policeman policemanMajor;

    @Enumerated(EnumType.STRING)
    private State state;

    public enum State {
        ON_WORK,
        FREEZE,
        CLOSE
    }
}
