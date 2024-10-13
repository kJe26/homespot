package edu.bbte.idde.mnim2165.mapper;

import edu.bbte.idde.mnim2165.dto.incoming.IncomingPropertyDto;
import edu.bbte.idde.mnim2165.dto.outgoing.*;
import edu.bbte.idde.mnim2165.model.Property;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class PropertyMapper {
    @IterableMapping(elementTargetType = PropertyReducedDto.class)
    public abstract Collection<PropertyReducedDto> modelsToReducedDtos(Collection<Property> properties);

    public abstract PropertyDetailsDto modelToDetailsDtos(Property property);

    public abstract UserSimpleDto detailsToSimpleDto(UserDetailsDto userDetailsDto);

    @Mapping(target = "id", ignore = true)
    public abstract Property dtoToModel(IncomingPropertyDto propertyDto);
}
