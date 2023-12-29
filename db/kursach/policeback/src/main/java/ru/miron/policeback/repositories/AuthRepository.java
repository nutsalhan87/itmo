package ru.miron.policeback.repositories;

import org.springframework.data.repository.Repository;
import ru.miron.policeback.entities.Auth;

import java.util.Optional;


public interface AuthRepository extends Repository<Auth, Long> {
    Optional<Auth> findByPolicemanSeries(String username);
}
