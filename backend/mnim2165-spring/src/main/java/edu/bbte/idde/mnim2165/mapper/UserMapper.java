package edu.bbte.idde.mnim2165.mapper;

import edu.bbte.idde.mnim2165.dto.incoming.IncomingUserDto;
import edu.bbte.idde.mnim2165.dto.outgoing.*;
import edu.bbte.idde.mnim2165.model.User;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @IterableMapping(elementTargetType = UserReducedDto.class)
    public abstract Collection<UserReducedDto> modelsToReducedDtos(Collection<User> users);

    public abstract UserDetailsDto modelToDetailsDtos(User user);

    public abstract PropertySimpleDto detailsToSimpleDto(PropertyDetailsDto propertyDetailsDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "properties", ignore = true)
    public abstract User dtoToModel(IncomingUserDto userDto);
}
