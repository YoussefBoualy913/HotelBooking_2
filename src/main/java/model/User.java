package model;
import model.enums.UserRole;

import java.util.UUID;
public class User {

    private UUID id;
    private String fullName;
    private String email;
    private String phone;
    private String passwordHash;
    private UserRole role;
    private byte[] salt;
    public User(UUID id,String fullName, String email, String phone,UserRole userRole, String passwordHash,byte[] salt) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.role = userRole;
        this.passwordHash = passwordHash;
        this.salt = salt;
    }

    public UUID getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String password) {
        this.passwordHash = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
    public byte[] getSalt() {
        return salt;
    }
    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public boolean isAdmin() {
        return role == UserRole.ADMIN;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}



