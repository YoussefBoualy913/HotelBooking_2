package repository.jdbc.mapper;

import model.User;
import model.enums.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserMapper {

    public static User map(ResultSet rs) throws SQLException {

        User user = new User(
                rs.getObject("id", UUID.class),
                rs.getString("fullname"),
                rs.getString("email"),
                rs.getString("phone"),
                UserRole.valueOf(rs.getString("role")),
                rs.getString("passwordhash"),
                rs.getBytes("salt")
        );
        user.setBalance(rs.getBigDecimal("balance"));
        return user;
    }
}
