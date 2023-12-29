package ru.miron.policeback.controller.auth.service;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.miron.policeback.entities.Auth;
import ru.miron.policeback.repositories.AuthRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    AuthRepository authRepository;

    @Override
    public Optional<Auth> findBySeries(String series) {
        return authRepository.findByPolicemanSeries(series);
    }
}
