package edu.bbte.idde.mnim2165.dto.outgoing;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDetailsDto extends UserReducedDto {
    private Integer age;
    private String phoneNumber;
    private String address;
    private String pin;
    private List<PropertySimpleDto> properties;
}
