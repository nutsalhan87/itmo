package org.nutsalhan87.web4backend.service;

import org.nutsalhan87.web4backend.repository.ShotRepository;
import org.nutsalhan87.web4backend.model.Shot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShotService {
    private final ShotRepository shotRepository;

    @Autowired
    public ShotService(ShotRepository shotRepository) {
        this.shotRepository = shotRepository;
    }

    @Transactional
    public List<Shot> getShotsByUsername(String username) {
        return shotRepository.getShotsByUsername(username);
    }

    @Transactional
    public void addShot(Shot shot) {
        shotRepository.save(shot);
    }
}
