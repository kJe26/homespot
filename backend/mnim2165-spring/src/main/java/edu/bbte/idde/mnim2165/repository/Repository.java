package edu.bbte.idde.mnim2165.repository;

import edu.bbte.idde.mnim2165.model.BaseEntity;

import java.util.Collection;
import java.util.UUID;

public interface Repository<T extends BaseEntity> {
    T create(T entity);

    T updateById(T newEntity);

    void deleteById(UUID id);

    Collection<T> findAll();

    T findById(UUID id);
}
