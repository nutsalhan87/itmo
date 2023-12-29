package ru.miron.policeback.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@EqualsAndHashCode(of = {"crimeId", "minorId"})
@Getter
@Setter
@IdClass(PolicemanCase.Id.class)
public class PolicemanCase {

    @jakarta.persistence.Id
    @Column(name = "fk_crime")
    private Integer crimeId;

    @jakarta.persistence.Id
    @Column(name = "fk_policeman_minor")
    private Integer minorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "fk_crime", nullable = false)
    private Crime crime;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "fk_policeman_minor", nullable = false)
    private Policeman minor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_policeman_major")
    private Policeman major;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        ASSIGNED,
        REMOVED
    }

    @Data
    public static class Id {
        private Integer crimeId;
        private Integer minorId;
    }
}
