package edu.bbte.idde.mnim2165.dto.incoming;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class IncomingUserDto {
    @Size(max = 100)
    @NotNull
    private String firstName;
    @Size(max = 100)
    @NotNull
    private String lastName;
    @Positive
    @NotNull
    private Integer age;
    @Size(max = 15)
    @NotNull
    @Pattern(regexp = "\\d+", message = "Phone number must contain only numeric digits")
    private String phoneNumber;
    @Size(max = 100)
    @NotNull
    private String address;
    @NotNull
    private String password;
    @Size(max = 20)
    @NotNull
    @Pattern(regexp = "\\d+", message = "PIN must contain only numeric digits")
    private String pin;
}
