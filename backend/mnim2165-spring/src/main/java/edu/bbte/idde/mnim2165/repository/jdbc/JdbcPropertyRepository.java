package edu.bbte.idde.mnim2165.repository.jdbc;

import edu.bbte.idde.mnim2165.exception.RepositoryException;
import edu.bbte.idde.mnim2165.model.Property;
import edu.bbte.idde.mnim2165.model.User;
import edu.bbte.idde.mnim2165.repository.PropertyRepository;
import edu.bbte.idde.mnim2165.repository.UserRepository;
import edu.bbte.idde.mnim2165.repository.jdbc.util.PropertyMapBuilder;
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
public class JdbcPropertyRepository implements PropertyRepository {
    @Autowired
    private ConnectionManager connectionManager;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    protected void init() {
        Connection connection = connectionManager.getConnection();

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("create table if not exists Properties ("
                    + "id varchar(128) primary key,"
                    + "address varchar(128),"
                    + "description varchar(128),"
                    + "propertyType varchar(1024),"
                    + "ownerId varchar(128),"
                    + "salePrice bigint,"
                    + "numberOfRooms bigint, "
                    + "area double)");
        } catch (SQLException e) {
            log.error("Error creating table", e);
        } finally {
            connectionManager.returnConnection(connection);
        }
    }

    @Override
    public Property create(Property property) {
        Connection connection = null;
        try {
            connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO Properties (`id`, `address`, `description`, `propertyType`, `ownerId`, "
                            + "`salePrice`, `numberOfRooms`, `area`)"
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            property.setId(property.getNewId());
            preparedStatement.setString(1, property.getId());
            preparedStatement.setString(2, property.getAddress());
            preparedStatement.setString(3, property.getDescription());
            preparedStatement.setString(4, property.getPropertyType());

            try {
                User user = userRepository.findById(UUID.fromString(property.getOwner().getId()));

                if (user == null) {
                    throw new RepositoryException("No user has been found with ID: " + property.getOwner().getId());
                }
            } catch (IllegalArgumentException e) {
                throw new RepositoryException(e.getMessage());
            }

            preparedStatement.setString(5, property.getOwner().getId());
            preparedStatement.setInt(6, property.getSalePrice());
            preparedStatement.setInt(7, property.getNumberOfRooms());
            preparedStatement.setDouble(8, property.getArea());

            preparedStatement.executeUpdate();

            log.info("Property inserted succesfully");
            return property;

        } catch (SQLException e) {
            log.error("Property insertion failed: " + e.getMessage());
            throw new RepositoryException("Property insertion failed: " + e.getMessage());
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
    }

    @Override
    public Property updateById(Property newProperty) {
        Connection connection = null;

        try {
            connection = connectionManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE `Properties`"
                            + "SET `address` = ?, `description` = ?, `propertyType` = ?, `salePrice` = ?, "
                            + "`numberOfRooms` = ?, `area` = ?, `ownerId` = ?"
                            + "WHERE (`id` = ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, newProperty.getAddress());
            preparedStatement.setString(2, newProperty.getDescription());
            preparedStatement.setString(3, newProperty.getPropertyType());
            preparedStatement.setInt(4, newProperty.getSalePrice());
            preparedStatement.setInt(5, newProperty.getNumberOfRooms());
            preparedStatement.setDouble(6, newProperty.getArea());

            try {
                User user = userRepository.findById(UUID.fromString(newProperty.getOwner().getId()));

                if (user == null) {
                    throw new RepositoryException("No user has been found with ID: " + newProperty.getOwner().getId());
                }
            } catch (IllegalArgumentException e) {
                throw new RepositoryException(e.getMessage());
            }

            preparedStatement.setString(7, newProperty.getOwner().getId());
            preparedStatement.setString(8, newProperty.getId());
            preparedStatement.executeUpdate();

            log.info("Property updated succesfully");
            return newProperty;
        } catch (SQLException e) {
            log.error("Property update failed", e);
            throw new RepositoryException("Property update failed", e);
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
    }

    @Override
    public void deleteByAddress(String address) {}

    @Override
    public void deleteById(UUID id) {
        Connection connection = null;

        try {
            connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `Properties` "
                            + "WHERE (`id` = ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, id.toString());
            preparedStatement.executeUpdate();

            log.info("Property deleted succesfully");
        } catch (SQLException e) {
            log.error("Property deletion failed!", e);
            throw new RepositoryException("Property deletion failed!", e);
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
    }

    @Override
    public Collection<Property> findAll() {
        Connection connection = null;
        try {
            connection = connectionManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `Properties`");
            ResultSet resultSet = preparedStatement.executeQuery();

            return PropertyMapBuilder.getUuidPropertyCollection(resultSet, userRepository);
        } catch (SQLException e) {
            log.error("Error findig all properties!");
            throw new RepositoryException("Error finding all properties!");
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
    }

    @Override
    public Property findById(UUID id) {
        Connection connection = null;

        try {
            connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `Properties` "
                    + "WHERE (`id` = ?)");
            preparedStatement.setString(1, id.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }

            Property property = PropertyMapBuilder.mapResultSetToProperty(resultSet, userRepository);

            log.info("Found property with ID: " + id);

            return property;
        } catch (SQLException e) {
            log.error("Error finding your property!", e);
            throw new RepositoryException("Error finding your property!", e);
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
    }

    @Override
    public Collection<Property> findByAddress(String address) {
        Connection connection = null;

        try {
            connection = connectionManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `Properties` "
                    + "WHERE (`address` = ?)");
            preparedStatement.setString(1, address);
            ResultSet resultSet = preparedStatement.executeQuery();

            return PropertyMapBuilder.getUuidPropertyCollection(resultSet, userRepository);
        } catch (SQLException e) {
            log.error("Error finding your properties", e);
            throw new RepositoryException("Error finding your properties", e);
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
    }
}
