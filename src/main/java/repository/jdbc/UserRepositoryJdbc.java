package repository.jdbc;

import db.DatabaseConnection;
import exception.UserNotFoundException;
import model.Room;
import model.User;
import model.enums.UserRole;
import repository.UserRepository;
import repository.jdbc.mapper.UserMapper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserRepositoryJdbc implements UserRepository {
    private Connection connection;

    public UserRepositoryJdbc() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    public User create(User user){
       String sql= """
                insert into users(id,fullname,email,phone, passwordHash, salt,role) values(?,?,?,?,?,?,?)
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setObject(1, user.getId());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getPasswordHash());
            ps.setBytes(6, user.getSalt());
            ps.setString(7, user.getRole().name());

            ps.executeUpdate();
            return user;
        }catch (SQLException e){
            throw new RuntimeException(
                    "Error creating user", e
            );
        }

    }

    @Override
    public Optional<User> findByEmail(String email) {

        String sql = """
            SELECT id, fullname, email, phone,
                   passwordhash, salt, role,balance
            FROM users
            WHERE email = ?
            """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return Optional.of(UserMapper.map(resultSet));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Error finding user by email", e
            );
        }

        return Optional.empty();
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = """
            SELECT EXISTS (
                SELECT 1
                FROM users
                WHERE email = ?
            )
            """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erreur lors de la vérification de l'email", e
            );
        }

        return false;
    }

    public void update(User user){
        String sql = """
            UPDATE users set fullname = ?, email = ?, phone = ? WHERE id = ?
        """;

        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1,user.getFullName());
            ps.setString(2,user.getEmail());
            ps.setString(3,user.getPhone());
            ps.setObject(4,user.getId());
            ps.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException( "Erreur lors  de modéfication de users", e);
        }
    }

    public void updatePassword(User user){
        String sql = """
            UPDATE users set passwordhash = ?, salt = ? WHERE id = ?
        """;

        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1,user.getPasswordHash());
            ps.setBytes(2,user.getSalt());
            ps.setObject(3,user.getId());
            ps.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException( "Erreur lors  de modéfication de password ", e);
        }
    }


    public void updateBalance(User user, BigDecimal newBalance) {
        String sql = """
            UPDATE users
            SET balance = ?
            WHERE id = ?
            """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBigDecimal(1, newBalance);
            statement.setObject(2, user.getId());

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated == 0) {
                throw new UserNotFoundException("User not found: " + user.getId());
            }

            user.setBalance(newBalance);

        } catch (SQLException e) {
            throw new RuntimeException("Error updating user balance", e);
        }
    }

    @Override
    public List<User> findAll() {
       String sql = """
                select * from users """;
        try (PreparedStatement statement = connection.prepareStatement(sql)){

            try(ResultSet rs = statement.executeQuery()){
                List<User> users = new ArrayList<>();
                while (rs.next()) {
                    users.add(UserMapper.map(rs));
                }
                return users;
            }
        } catch (SQLException e) {
            throw new RuntimeException("errur lore de chargement de users"+e);
        }
    }
}