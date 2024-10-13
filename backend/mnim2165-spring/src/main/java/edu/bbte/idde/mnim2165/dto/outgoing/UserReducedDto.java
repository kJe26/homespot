package edu.bbte.idde.mnim2165.dto.outgoing;

import lombok.Data;

import java.util.UUID;

@Data
public class UserReducedDto {
    private UUID id;
    private String firstName;
    private String lastName;
}
