package edu.bbte.idde.mnim2165.controller;

import edu.bbte.idde.mnim2165.dto.incoming.IncomingPropertyDto;
import edu.bbte.idde.mnim2165.dto.outgoing.PropertyDetailsDto;
import edu.bbte.idde.mnim2165.dto.outgoing.PropertyReducedDto;
import edu.bbte.idde.mnim2165.exception.ResourceNotFoundException;
import edu.bbte.idde.mnim2165.mapper.PropertyMapper;
import edu.bbte.idde.mnim2165.model.Property;
import edu.bbte.idde.mnim2165.model.User;
import edu.bbte.idde.mnim2165.repository.JpaUserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/users/{userId}")
@Profile("jpa")
public class UserPropertiesController {
    @Autowired
    private PropertyMapper propertyMapper;

    @Autowired
    private JpaUserRepository userRepository;

    @GetMapping("/properties")
    public Collection<PropertyReducedDto> getAllUserProperties(@PathVariable("userId") String userId,
                                                               @RequestParam(required = false) String address) {
        UUID ownerId = UUID.fromString(userId);
        User owner = userRepository.findById(ownerId);

        if (owner == null) {
            throw new ResourceNotFoundException("No user with id: " + userId + " has been found");
        }

        Collection<Property> properties = userRepository.findById(ownerId).getProperties();

        if (address == null) {
            return propertyMapper.modelsToReducedDtos(properties);
        }

        return propertyMapper.modelsToReducedDtos(properties.stream()
                .filter(property -> property.getAddress().equals(address))
                .collect(Collectors.toList()));
    }

    @GetMapping("/properties/{propertyId}")
    public PropertyDetailsDto getUserProperty(@PathVariable("userId") String userId,
                                              @PathVariable("propertyId") String id) {
        UUID ownerId = UUID.fromString(userId);
        User owner = userRepository.findById(ownerId);

        if (owner == null) {
            throw new ResourceNotFoundException("No user with id: " + userId + " has been found");
        }

        Optional<Property> foundProperty = userRepository.findById(ownerId).getProperties()
                .stream().filter(property -> property.getId().equals(id)).findFirst();

        if (foundProperty.isEmpty()) {
            throw new ResourceNotFoundException("No property with id: " + id + " has been found");
        }

        return propertyMapper.modelToDetailsDtos(foundProperty.get());
    }

    @PostMapping("/properties")
    public PropertyDetailsDto createProperty(@PathVariable("userId") String userId,
                                             @RequestBody @Valid IncomingPropertyDto incomingPropertyDto) {
        UUID ownerId = UUID.fromString(userId);
        User owner = userRepository.findById(ownerId);

        if (owner == null) {
            throw new ResourceNotFoundException("No user with id: " + userId + " has been found");
        }

        Property newProperty = propertyMapper.dtoToModel(incomingPropertyDto);
        newProperty.setOwner(owner);
        owner.getProperties().add(newProperty);
        List<String> currentIds = owner.getProperties().stream().map(Property::getId).toList();
        userRepository.save(owner);

        return propertyMapper.modelToDetailsDtos(owner.getProperties().stream()
                .filter(property -> !currentIds.contains(property.getId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Property creation failed")));
    }

    @DeleteMapping("/properties/{propertyId}")
    public String deleteProperty(@PathVariable("userId") String userId, @PathVariable("propertyId") String id) {
        UUID ownerId = UUID.fromString(userId);
        User owner = userRepository.findById(ownerId);

        if (owner == null) {
            throw new ResourceNotFoundException("No user with id: " + userId + " has been found");
        }

        Optional<Property> foundProperty = userRepository.findById(ownerId).getProperties()
                        .stream().filter(property -> property.getId().equals(id)).findFirst();

        if (foundProperty.isEmpty()) {
            throw new ResourceNotFoundException("No property with id: " + id + " has been found");
        }

        foundProperty.get().setOwner(null);
        owner.getProperties().remove(foundProperty.get());
        userRepository.save(owner);
        return id;
    }
}
