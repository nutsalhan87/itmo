package org.nutsalhan87.web4backend.service;

import org.nutsalhan87.web4backend.model.User;
import org.nutsalhan87.web4backend.repository.UserRepository;
import org.nutsalhan87.web4backend.util.PasswordProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordProcessor passwordProcessor;

    @Autowired
    public UserService(UserRepository userRepository, PasswordProcessor passwordProcessor) {
        this.userRepository = userRepository;
        this.passwordProcessor = passwordProcessor;
    }

    @Transactional
    public Long addUser(String username, String password) {
        if (userRepository.findUserByUsername(username).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Такой пользователь уже существует");
        }
        Long token = passwordProcessor.getToken();
        String encoded = passwordProcessor.encode(password);
        User user = new User(username, encoded, token);
        userRepository.save(user);

        return token;
    }

    @Transactional
    public Long signIn(String username, String password) {
        if (userRepository.findUserByUsername(username).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Пользователя с таким именем пользователя нет");
        }
        User user = userRepository.findUserByUsername(username).get();
        if (!passwordProcessor.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Неверный пароль");
        }
        Long token = passwordProcessor.getToken();
        user.setToken(token);
        userRepository.save(user);

        return token;
    }

    @Transactional
    public void throwIfTokenInvalid(Long token) {
        Optional<Long> optional = userRepository.findToken(token);
        if (optional.isEmpty() || optional.get() == 0L) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Ошибочный токен");
        }
    }

    @Transactional
    public String getUsernameByToken(Long token) {
        throwIfTokenInvalid(token);
        return userRepository.getUsernameByToken(token).get();
    }

    @Transactional
    public void makeTokenNull(Long token) {
        throwIfTokenInvalid(token);
        userRepository.makeTokenNull(token);
    }
}
