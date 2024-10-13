package edu.bbte.idde.mnim2165.repository.jdbc;

import edu.bbte.idde.mnim2165.exception.RepositoryException;
import edu.bbte.idde.mnim2165.model.User;
import edu.bbte.idde.mnim2165.repository.UserRepository;
import edu.bbte.idde.mnim2165.repository.jdbc.util.UserMapBuilder;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Collection;
import java.util.UUID;

@Slf4j
@Repository
@Profile("jdbc")
public class JdbcUserRepository implements UserRepository {
    @Autowired
    private ConnectionManager connectionManager;

    @PostConstruct
    protected void init() {
        Connection connection = connectionManager.getConnection();

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("create table if not exists Users ("
                    + "id varchar(128) primary key,"
                    + "firstName varchar(128),"
                    + "lastName varchar(128),"
                    + "phoneNumber varchar(128) unique,"
                    + "address varchar(128),"
                    + "password varchar(128),"
                    + "pin varchar(128) unique,"
                    + "age bigint)");
        } catch (SQLException e) {
            log.error("Error creating table", e);
        } finally {
            connectionManager.returnConnection(connection);
        }
    }

    @Override
    public User create(User user) {
        Connection connection = null;
        try {
            connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO Users (`id`, `firstName`, `lastName`, `phoneNumber`, `address`, "
                            + "`password`, `pin`, `age`)"
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            user.setId(user.getNewId());
            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getPhoneNumber());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.setString(7, user.getPin());
            preparedStatement.setInt(8, user.getAge());

            preparedStatement.executeUpdate();

            log.info("User inserted succesfully");
            return user;
        } catch (SQLException e) {
            log.error("User insertion failed: " + e.getMessage());
            throw new RepositoryException("User insertion failed: " + e);
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
    }

    @Override
    public User updateById(User newUser) {
        Connection connection = null;

        try {
            connection = connectionManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE `Users`"
                            + "SET `firstName` = ?, `lastName` = ?, `phoneNumber` = ?, `address` = ?, "
                            + "`password` = ?, `pin` = ?,"
                            + "`age` = ? WHERE (`id` = ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, newUser.getFirstName());
            preparedStatement.setString(2, newUser.getLastName());
            preparedStatement.setString(3, newUser.getPhoneNumber());
            preparedStatement.setString(4, newUser.getAddress());
            preparedStatement.setString(5, newUser.getPassword());
            preparedStatement.setString(6, newUser.getPin());
            preparedStatement.setInt(7, newUser.getAge());
            preparedStatement.setString(8, newUser.getId());
            preparedStatement.executeUpdate();

            log.info("User updated succesfully");

            return newUser;
        } catch (SQLException e) {
            log.error("User update failed", e);
            throw new RepositoryException("User update failed", e);
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
    }

    @Override
    public void deleteById(UUID id) {
        Connection connection = null;

        try {
            connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `Users` "
                            + "WHERE (`id` = ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, id.toString());
            preparedStatement.executeUpdate();

            log.info("User deleted succesfully");
        } catch (SQLException e) {
            log.error("User deletion failed!", e);
            throw new RepositoryException("User deletion failed!", e);
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
    }

    @Override
    public Collection<User> findAll() {
        Connection connection = null;
        try {
            connection = connectionManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `Users`");
            ResultSet resultSet = preparedStatement.executeQuery();

            return UserMapBuilder.getUuidUserCollection(resultSet);
        } catch (SQLException e) {
            log.error("Error findig all users!");
            throw new RepositoryException("Error finding all users!");
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
    }

    @Override
    public User findById(UUID id) {
        Connection connection = null;

        try {
            connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `Users` "
                    + "WHERE (`id` = ?)");
            preparedStatement.setString(1, id.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }

            User user = UserMapBuilder.mapResultSetToUser(resultSet);

            log.info("Found user with ID: " + id);

            return user;
        } catch (SQLException e) {
            log.error("Error finding user!", e);
            throw new RepositoryException("Error finding user!", e);
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
    }

    @Override
    public User findByPin(String pin) {
        Connection connection = null;

        try {
            connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `Users` "
                    + "WHERE (`pin` = ?)");
            preparedStatement.setString(1, pin);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }

            User user = UserMapBuilder.mapResultSetToUser(resultSet);

            log.info("Found user with PIN: " + pin);

            return user;
        } catch (SQLException e) {
            log.error("Error finding user!", e);
            throw new RepositoryException("Error finding user!", e);
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
    }
}
