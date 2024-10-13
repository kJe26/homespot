package edu.bbte.idde.mnim2165.repository.mem;

import edu.bbte.idde.mnim2165.exception.RepositoryException;
import edu.bbte.idde.mnim2165.model.Property;
import edu.bbte.idde.mnim2165.model.User;
import edu.bbte.idde.mnim2165.repository.PropertyRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Repository
@Profile("dev")
public class PropertyMemRepository implements PropertyRepository {
    private Map<UUID, Property> properties;

    @Autowired
    private UserMemRepository userRepository;

    @PostConstruct
    protected void init() {
        this.properties = new HashMap<>();
    }

    @Override
    public Property create(Property property) {
        Property newProperty = new Property();
        newProperty.setId(property.getNewId());
        newProperty.setAddress(property.getAddress());
        newProperty.setPropertyType(property.getPropertyType());
        newProperty.setArea(property.getArea());
        newProperty.setDescription(property.getDescription());
        newProperty.setSalePrice(property.getSalePrice());
        newProperty.setNumberOfRooms(property.getNumberOfRooms());

        try {
            User user = userRepository.findById(UUID.fromString(property.getOwner().getId()));

            if (user == null) {
                throw new RepositoryException("No user has been found with ID: " + property.getOwner().getId());
            }

            newProperty.setOwner(user);
        } catch (IllegalArgumentException e) {
            throw new RepositoryException(e.getMessage());
        }


        this.properties.put(UUID.fromString(newProperty.getId()), newProperty);

        log.info("New Property has been created succesfully");

        return newProperty;
    }

    @Override
    public Property updateById(Property newProperty) {
        Property updatedProperty = this.properties.get(UUID.fromString(newProperty.getId()));

        if (updatedProperty != null) {
            updatedProperty.setAddress(newProperty.getAddress());
            updatedProperty.setPropertyType(newProperty.getPropertyType());
            updatedProperty.setArea(newProperty.getArea());
            updatedProperty.setDescription(newProperty.getDescription());
            updatedProperty.setSalePrice(newProperty.getSalePrice());
            updatedProperty.setNumberOfRooms(newProperty.getNumberOfRooms());

            try {
                User user = userRepository.findById(UUID.fromString(newProperty.getOwner().getId()));

                if (user == null) {
                    throw new RepositoryException("No user has been found with ID: " + newProperty.getOwner().getId());
                }

                updatedProperty.setOwner(user);
            } catch (IllegalArgumentException e) {
                throw new RepositoryException(e.getMessage());
            }


            log.info("Property with ID: " + updatedProperty.getId() + " has been updated succesfully");
        } else {
            log.info("Property with ID: " + newProperty.getId() + " was not found");
        }

        return updatedProperty;
    }

    @Override
    public void deleteByAddress(String address) {
        properties.entrySet().removeIf(entry -> address.equals(entry.getValue().getAddress()));

        log.info("Properties with address: " + address + " have been deleted succesfully");
    }

    @Override
    public void deleteById(UUID id) {
        Property deletedProperty = this.properties.get(id);

        if (deletedProperty != null) {
            this.properties.entrySet().removeIf(entry -> id.toString().equals(entry.getValue().getId()));

            log.info("Property with ID: " + id.toString() + " has been deleted succesfully");
        } else {
            log.info("Property with ID: " + id.toString() + " has not been found");
        }
    }


    @Override
    public Collection<Property> findAll() {
        return this.properties.values();
    }

    @Override
    public Property findById(UUID id) {
        Property foundProperty = this.properties.get(id);

        if (foundProperty != null) {
            log.info("property with ID: " + id + " has been found");
        }

        return foundProperty;
    }

    @Override
    public Collection<Property> findByAddress(String address) {
        return properties.values().stream()
                .filter(property -> address.equals(property.getAddress()))
                .collect(Collectors.toList());
    }
}
