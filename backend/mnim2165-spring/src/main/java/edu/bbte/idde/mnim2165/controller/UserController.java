package edu.bbte.idde.mnim2165.controller;

import edu.bbte.idde.mnim2165.dto.incoming.IncomingUserDto;
import edu.bbte.idde.mnim2165.dto.outgoing.UserDetailsDto;
import edu.bbte.idde.mnim2165.dto.outgoing.UserReducedDto;
import edu.bbte.idde.mnim2165.exception.ResourceNotFoundException;
import edu.bbte.idde.mnim2165.mapper.UserMapper;
import edu.bbte.idde.mnim2165.model.User;
import edu.bbte.idde.mnim2165.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public Collection<UserReducedDto> getAllUsers() {
        return userMapper.modelsToReducedDtos(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public UserDetailsDto getUserById(@PathVariable String id) {
        User user = userRepository.findById(UUID.fromString(id));
        if (user == null) {
            throw new ResourceNotFoundException("No user has been found with ID: " + id);
        }
        return userMapper.modelToDetailsDtos(user);
    }

    @PostMapping
    public UserDetailsDto createUser(@RequestBody @Valid IncomingUserDto incomingUserDto) {
        User user = userMapper.dtoToModel(incomingUserDto);
        user = userRepository.create(user);
        return userMapper.modelToDetailsDtos(user);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id) {
        User user = userRepository.findById(UUID.fromString(id));
        if (user == null) {
            throw new ResourceNotFoundException("No user has been found with ID: " + id);
        }
        userRepository.deleteById(UUID.fromString(id));
        return id;
    }

    @PutMapping("/{id}")
    public UserDetailsDto updateUser(@PathVariable String id, @RequestBody @Valid IncomingUserDto incomingUserDto) {
        User user = userRepository.findById(UUID.fromString(id));

        if (user == null) {
            throw new ResourceNotFoundException("No user has been found with ID: " + id);
        }

        user = userMapper.dtoToModel(incomingUserDto);
        user.setId(id);

        return userMapper.modelToDetailsDtos(userRepository.updateById(user));
    }
}
