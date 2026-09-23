package service;
import exception.EmailAlreadyExistsException;
import exception.InvalidCredentialsException;
import model.User;
import model.enums.UserRole;
import repository.UserRepository;
import util.*;

import java.util.Optional;
import java.util.UUID;

public class AuthService {
    private UserRepository userRepository;
    private User currentUser;
    private InputUtils inputUtils;

    public AuthService(UserRepository userRepository,InputUtils inputUtils) {
        this.userRepository = userRepository;
        this.inputUtils = inputUtils;
    }

    public User register() {


        String fullName =  inputUtils.readString("Fullname:");
        if (!ValidationUtils.isNotEmpty(fullName)) {
            throw new IllegalArgumentException("Full Name cannot be empty");
        }
        String email = inputUtils.readString("Email:");
        if (!ValidationUtils.isValidEmail(email)) {
            throw new IllegalArgumentException("Email must contain a '@' ");
        }

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        String phone = inputUtils.readString("Phone:");
        if (!ValidationUtils.isValidPhone(phone)) {
            throw new IllegalArgumentException("phone number invalide.");
        }

        String password = inputUtils.readString("Password:");
        if (!ValidationUtils.isValidPassword(password)) {
            throw new IllegalArgumentException("password must contain at least 6 characters");
        }

        byte[] salt = PasswordUtils.generateSalt();

        String passwordHash =
                PasswordUtils.hashPassword(password, salt);

        User user = new User(
                UUID.randomUUID(),
                fullName,
                email,
                phone,
                UserRole.CLIENT,
                passwordHash,
                salt
        );

        userRepository.create(user);

        return user;
    }

    public User login() {



        String email = inputUtils.readString("Email:");
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new InvalidCredentialsException("Invalid credentials.");
        }
        String password = inputUtils.readString("Password:");
        if (!user.get().getPasswordHash().equals(PasswordUtils.hashPassword(password,user.get().getSalt()))) {
            throw new InvalidCredentialsException("Invalid credentials.");
        }

        currentUser = user.get();

        return currentUser;
    }
    public User autoLogin() {
        Optional<User> user = userRepository.findByEmail("youssef@gmail.com");
        currentUser = user.get();

        return currentUser;
    }

    public void logout() {
        currentUser = null;
    }


    public User getCurrentUser() {
        return currentUser;
    }

}