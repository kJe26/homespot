package edu.bbte.idde.mnim2165.dto.incoming;

import edu.bbte.idde.mnim2165.model.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class IncomingPropertyDto {
    @Size(max = 100)
    @NotNull
    private String address;
    @Positive
    @NotNull
    private Integer salePrice;
    @Size(max = 8096)
    @NotNull
    private String description;
    @Positive
    @NotNull
    private Integer numberOfRooms;
    @Size(max = 100)
    @NotNull
    private String propertyType;
    @Positive
    @NotNull
    private Float area;
    @NotNull
    private User owner;
}
