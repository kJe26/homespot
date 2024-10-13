package edu.bbte.idde.mnim2165.dto.outgoing;

import lombok.Data;

import java.util.UUID;

@Data
public class UserSimpleDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String phoneNumber;
    private String address;
    private String pin;
}
