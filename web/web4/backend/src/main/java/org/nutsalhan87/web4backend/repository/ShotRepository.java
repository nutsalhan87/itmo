package org.nutsalhan87.web4backend.repository;

import org.nutsalhan87.web4backend.model.Shot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShotRepository extends JpaRepository<Shot, Long> {
    List<Shot> getShotsByUsername(String username);
}
