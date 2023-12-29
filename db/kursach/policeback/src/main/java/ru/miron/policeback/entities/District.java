package ru.miron.policeback.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.Set;

import lombok.*;


@Entity
@EqualsAndHashCode(of = {"id"})
@Getter
@Setter
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "precinct_district",
            joinColumns = @JoinColumn(name = "fk_district"),
            inverseJoinColumns = @JoinColumn(name = "fk_precinct")
    )
    private Set<Precinct> precinctsInside;

    @OneToMany(mappedBy = "district")
    private Set<Crime> districtCrimes;

}
