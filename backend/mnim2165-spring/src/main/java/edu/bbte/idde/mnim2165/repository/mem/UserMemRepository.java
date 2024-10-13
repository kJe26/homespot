package edu.bbte.idde.mnim2165.repository.mem;

import edu.bbte.idde.mnim2165.model.User;
import edu.bbte.idde.mnim2165.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
@Profile("dev")
public class UserMemRepository implements UserRepository {
    private Map<UUID, User> users;

    @PostConstruct
    protected void init() {
        this.users = new HashMap<>();
    }

    @Override
    public User create(User user) {
        User newUser = new User();
        newUser.setId(UUID.randomUUID().toString());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setAddress(user.getAddress());
        newUser.setPassword(user.getPassword());
        newUser.setPin(user.getPin());
        newUser.setAge(user.getAge());
        newUser.setProperties(new ArrayList<>());

        this.users.put(UUID.fromString(newUser.getId()), newUser);

        log.info("New user has been created succesfully");

        return newUser;
    }

    @Override
    public User updateById(User newUser) {
        User updatedUser = this.users.get(UUID.fromString(newUser.getId()));

        if (updatedUser != null) {
            updatedUser.setFirstName(newUser.getFirstName());
            updatedUser.setLastName(newUser.getLastName());
            updatedUser.setPhoneNumber(newUser.getPhoneNumber());
            updatedUser.setAddress(newUser.getAddress());
            updatedUser.setPassword(newUser.getPassword());
            updatedUser.setPin(newUser.getPin());
            updatedUser.setAge(newUser.getAge());

            log.info("User with ID: " + updatedUser.getId() + " has been updated succesfully");
        } else {
            log.info("User with ID: " + newUser.getId() + " was not found");
        }

        return updatedUser;
    }

    @Override
    public void deleteById(UUID id) {
        User deletedUser = this.users.get(id);

        if (deletedUser != null) {
            this.users.entrySet().removeIf(entry -> id.toString().equals(entry.getValue().getId()));

            log.info("User with ID: " + id.toString() + " has been deleted succesfully");
        } else {
            log.info("User with ID: " + id.toString() + " has not been found");
        }
    }

    @Override
    public Collection<User> findAll() {
        return this.users.values();
    }

    @Override
    public User findById(UUID id) {
        User foundUser = this.users.get(id);

        if (foundUser != null) {
            log.info("User with ID: " + id + " has been found");
        }

        return foundUser;
    }

    @Override
    public User findByPin(String pin) {
        return users.values().stream()
                .filter(user -> pin.equals(user.getPin()))
                .findFirst()
                .orElse(null);
    }
}
