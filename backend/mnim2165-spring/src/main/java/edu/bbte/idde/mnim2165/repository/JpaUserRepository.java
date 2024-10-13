package edu.bbte.idde.mnim2165.repository;

import edu.bbte.idde.mnim2165.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("jpa")
public interface JpaUserRepository extends JpaRepository<User, String>, UserRepository {
    @Override
    User findByPin(String pin);

    @Override
    default User updateById(User newUser) {
        User current = getReferenceById(newUser.getId());

        if (newUser.getPassword().isEmpty()) {
            newUser.setPassword(current.getPassword());
        }

        current.setFirstName(newUser.getFirstName());
        current.setLastName(newUser.getLastName());
        current.setAge(newUser.getAge());
        current.setPhoneNumber(newUser.getPhoneNumber());
        current.setAddress(newUser.getAddress());
        current.setPassword(newUser.getPassword());
        current.setPin(newUser.getPin());

        return saveAndFlush(current);
    }

    @Override
    default User create(User user) {
        saveAndFlush(user);
        return user;
    }

    @Override
    default User findById(@Param("id") UUID id) {
        Optional<User> user = findById(id.toString());

        return user.orElse(null);
    }

    @Override
    default void deleteById(@Param("id") UUID id) {
        deleteById(id.toString());
    }
}
