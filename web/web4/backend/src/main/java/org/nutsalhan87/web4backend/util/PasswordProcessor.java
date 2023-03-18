package org.nutsalhan87.web4backend.util;

import org.nutsalhan87.web4backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PasswordProcessor {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final Random tokenizer = new Random();

    private final UserRepository userRepository;

    @Autowired
    public PasswordProcessor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String encode(String query) {
        return encoder.encode(query);
    }

    public boolean matches(String decoded, String encoded) {
        return encoder.matches(decoded, encoded);
    }

    public Long getToken() {
        long token;
        do {
            token = tokenizer.nextLong(Long.MAX_VALUE);
        } while(userRepository.findToken(token).isPresent() || token == 0L);
        return token;
    }
}
