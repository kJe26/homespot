package edu.bbte.idde.mnim2165.repository;

import edu.bbte.idde.mnim2165.model.Property;

import java.util.Collection;

public interface PropertyRepository extends Repository<Property> {
    void deleteByAddress(String address);

    Collection<Property> findByAddress(String address);
}
