package ru.miron.policeback.controller.auth.service;

import ru.miron.policeback.entities.Auth;

import java.util.Optional;

public interface AuthService {
    Optional<Auth> findBySeries(String username);
}
