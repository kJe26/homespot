package edu.bbte.idde.mnim2165.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "Properties")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Property extends BaseEntity {
    @Column(nullable = false, length = 100)
    private String address;
    @Column(nullable = false)
    @Positive(message = "Sale price cannot be negative")
    private Integer salePrice;
    @Column(length = 8096)
    private String description;
    @Column(nullable = false)
    @Positive(message = "Number of rooms cannot be negative")
    private Integer numberOfRooms;
    @Column(nullable = false, length = 100)
    private String propertyType;
    @Column(nullable = false)
    @Positive(message = "Area cannot be negative")
    private Float area;
    @ManyToOne(optional = false)
    @JoinColumn(name = "ownerId", referencedColumnName = "id")
    private User owner;
}
