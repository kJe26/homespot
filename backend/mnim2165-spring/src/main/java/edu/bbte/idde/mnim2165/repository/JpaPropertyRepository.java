package edu.bbte.idde.mnim2165.repository;

import edu.bbte.idde.mnim2165.model.Property;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("jpa")
public interface JpaPropertyRepository extends JpaRepository<Property, String>, PropertyRepository {
    @Override
    @Transactional
    @Modifying
    void deleteByAddress(String address);

    @Override
    Collection<Property> findByAddress(String address);

    @Override
    default Property updateById(Property newProperty) {
        saveAndFlush(newProperty);
        return newProperty;
    }

    @Override
    default Property create(Property property) {
        saveAndFlush(property);
        return property;
    }

    @Override
    default Property findById(@Param("id") UUID id) {
        Optional<Property> property = findById(id.toString());

        return property.orElse(null);
    }

    @Override
    default void deleteById(@Param("id") UUID id) {
        deleteById(id.toString());
    }
}
