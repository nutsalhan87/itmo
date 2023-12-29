package ru.miron.policeback.entities;

import jakarta.persistence.*;

import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Entity
@EqualsAndHashCode(of = {"number"})
@Getter
@Setter
public class Precinct {

    @Id
    @Column(nullable = false, updatable = false)
    private Integer number;

    @OneToMany(mappedBy = "precinct")
    private Set<Policeman> policemenInside;

    @ManyToMany(mappedBy = "precinctsInside")
    private Set<District> districts;
}
