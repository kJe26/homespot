package edu.bbte.idde.mnim2165.dto.outgoing;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PropertyDetailsDto extends PropertyReducedDto {
    private String description;
    private UserSimpleDto owner;
}
