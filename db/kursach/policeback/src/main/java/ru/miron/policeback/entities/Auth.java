package ru.miron.policeback.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@EqualsAndHashCode(of = {"policemanId"})
@Getter
@Setter
public class Auth {
    @Id
    @Column(name = "fk_policeman")
    private Integer policemanId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "fk_policeman")
    private Policeman policeman;

    @Column(name = "hashed", nullable = false)
    private String hashed;
}
