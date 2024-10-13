package edu.bbte.idde.mnim2165.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Users")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @Column(nullable = false, length = 100)
    private String firstName;
    @Column(nullable = false, length = 100)
    private String lastName;
    @Column(nullable = false)
    @Positive(message = "Age cannot be negative")
    private Integer age;
    @Column(nullable = false, length = 15, unique = true)
    private String phoneNumber;
    @Column(nullable = false, length = 100)
    private String address;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, length = 20, unique = true)
    private String pin;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Property> properties;

    @Override
    public String toString() {
        return "User{"
                + "firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", age=" + age
                + ", phoneNumber='" + phoneNumber + '\''
                + ", address='" + address + '\''
                + ", password='" + password + '\''
                + ", pin='" + pin + '\''
                + '}';
    }
}
