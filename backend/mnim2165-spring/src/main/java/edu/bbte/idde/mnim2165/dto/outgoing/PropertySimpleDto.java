package edu.bbte.idde.mnim2165.dto.outgoing;

import lombok.Data;

import java.util.UUID;

@Data
public class PropertySimpleDto {
    private UUID id;
    private String address;
    private Integer salePrice;
    private Integer numberOfRooms;
    private String propertyType;
    private Float area;
    private String description;
}
