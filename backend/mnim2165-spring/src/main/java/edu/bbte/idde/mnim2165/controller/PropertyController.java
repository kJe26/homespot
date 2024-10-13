package edu.bbte.idde.mnim2165.controller;

import edu.bbte.idde.mnim2165.dto.incoming.IncomingPropertyDto;
import edu.bbte.idde.mnim2165.dto.outgoing.PropertyDetailsDto;
import edu.bbte.idde.mnim2165.dto.outgoing.PropertyReducedDto;
import edu.bbte.idde.mnim2165.exception.ResourceNotFoundException;
import edu.bbte.idde.mnim2165.mapper.PropertyMapper;
import edu.bbte.idde.mnim2165.model.Property;
import edu.bbte.idde.mnim2165.model.User;
import edu.bbte.idde.mnim2165.repository.PropertyRepository;
import edu.bbte.idde.mnim2165.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/properties")
public class PropertyController {
    @Autowired
    private PropertyMapper propertyMapper;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public Collection<PropertyReducedDto> getProperties(@RequestParam(required = false) String address) {
        if (address == null) {
            return propertyMapper.modelsToReducedDtos(propertyRepository.findAll());
        }

        return propertyMapper.modelsToReducedDtos(propertyRepository.findByAddress(address));
    }

    @GetMapping("/{id}")
    public PropertyDetailsDto getPropertyById(@PathVariable String id) {
        Property property = propertyRepository.findById(UUID.fromString(id));
        if (property == null) {
            throw new ResourceNotFoundException("No property has been found with ID: " + id);
        }
        return propertyMapper.modelToDetailsDtos(property);
    }

    @PostMapping
    public PropertyDetailsDto createProperty(@RequestBody @Valid IncomingPropertyDto incomingPropertyDto) {
        Property property = propertyMapper.dtoToModel(incomingPropertyDto);
        property = propertyRepository.create(property);
        return propertyMapper.modelToDetailsDtos(property);
    }

    @DeleteMapping("/{id}")
    public String deleteProperty(@PathVariable String id) {
        Property property = propertyRepository.findById(UUID.fromString(id));
        if (property == null) {
            throw new ResourceNotFoundException("No property has been found with ID: " + id);
        }
        User owner = property.getOwner();
        if (owner != null) {
            userRepository.updateById(owner);
            if (owner.getProperties() != null) {
                owner.getProperties().remove(property);
            }
        }
        propertyRepository.deleteById(UUID.fromString(id));
        return id;
    }

    @PutMapping("/{id}")
    public PropertyDetailsDto updateProperty(@PathVariable String id,
                                             @RequestBody @Valid IncomingPropertyDto incomingPropertyDto) {
        Property property = propertyRepository.findById(UUID.fromString(id));

        if (property == null) {
            throw new ResourceNotFoundException("No property has been found with ID: " + id);
        }

        property = propertyMapper.dtoToModel(incomingPropertyDto);
        property.setId(id);

        return propertyMapper.modelToDetailsDtos(propertyRepository.updateById(property));
    }
}
