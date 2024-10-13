package edu.bbte.idde.mnim2165.repository.jdbc.util;

import edu.bbte.idde.mnim2165.model.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

@Slf4j
public class UserMapBuilder {
    public static Collection<User> getUuidUserCollection(ResultSet resultSet)
            throws SQLException {
        Collection<User> users = new LinkedList<>();

        while (resultSet.next()) {
            User user = mapResultSetToUser(resultSet);
            users.add(user);
        }
        log.info("Found all users succesfully");
        return users;
    }

    public static User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getString(1));
        user.setFirstName(resultSet.getString(2));
        user.setLastName(resultSet.getString(3));
        user.setPhoneNumber(resultSet.getString(4));
        user.setAddress(resultSet.getString(5));
        user.setPassword(resultSet.getString(6));
        user.setPin(resultSet.getString(7));
        user.setAge(Integer.parseInt(resultSet.getString(8)));

        return user;
    }
}
