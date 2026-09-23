package repository;

import model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    User create(User user);

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

//    Optional<User> findById(UUID id);
//
//    List<User> findAll();
//
    void update(User user);
   void updatePassword(User user);
//    void delete(UUID id);
}