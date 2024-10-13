package edu.bbte.idde.mnim2165.repository.jdbc.util;

import edu.bbte.idde.mnim2165.model.Property;
import edu.bbte.idde.mnim2165.model.User;
import edu.bbte.idde.mnim2165.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;

@Slf4j
@Repository
public class PropertyMapBuilder {
    public static Property mapResultSetToProperty(ResultSet resultSet, UserRepository userRepository)
            throws SQLException {
        Property property = new Property();
        property.setId(resultSet.getString(1));
        property.setAddress(resultSet.getString(2));
        property.setDescription(resultSet.getString(3));
        property.setPropertyType(resultSet.getString(4));
        User owner = userRepository.findById(UUID.fromString(resultSet.getString(5)));
        property.setOwner(owner);
        property.setSalePrice(Integer.parseInt(resultSet.getString(6)));
        property.setNumberOfRooms(Integer.parseInt(resultSet.getString(7)));
        property.setArea(Float.parseFloat(resultSet.getString(8)));

        return property;
    }

    public static Collection<Property> getUuidPropertyCollection(ResultSet resultSet, UserRepository userRepository)
            throws SQLException {
        Collection<Property> properties = new LinkedList<>();
        while (resultSet.next()) {
            Property property = mapResultSetToProperty(resultSet, userRepository);

            properties.add(property);
        }
        log.info("Found all properties succesfully");
        return properties;
    }
}
