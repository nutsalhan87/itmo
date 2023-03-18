package org.nutsalhan87.web4backend.repository;

import org.nutsalhan87.web4backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
    @Query("select u.username from User u where u.token = ?1")
    Optional<String> getUsernameByToken(Long token);
    @Query("select u.token from User u where u.token = ?1")
    Optional<Long> findToken(Long token);
    @Modifying
    @Query("update User u set u.token = 0 where u.token = ?1")
    void makeTokenNull(Long token);
}
