package ru.miron.policeback.repositories;

import ru.miron.policeback.entities.District;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DistrictRepository extends JpaRepository<District, Integer> {
}
