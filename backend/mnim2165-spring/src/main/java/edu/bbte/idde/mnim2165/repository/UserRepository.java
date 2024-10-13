package edu.bbte.idde.mnim2165.repository;

import edu.bbte.idde.mnim2165.model.User;

public interface UserRepository extends Repository<User> {

    User findByPin(String pin);
}
