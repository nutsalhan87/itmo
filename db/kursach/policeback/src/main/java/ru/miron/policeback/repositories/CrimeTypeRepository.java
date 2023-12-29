package ru.miron.policeback.repositories;

import ru.miron.policeback.entities.CrimeType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CrimeTypeRepository extends JpaRepository<CrimeType, Integer> {
}
