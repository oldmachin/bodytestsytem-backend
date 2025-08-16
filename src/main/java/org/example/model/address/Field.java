package org.example.model.address;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @Column(nullable = false, unique = true)
    private String addressName;

    @Column(nullable = false, unique = true)
    private String addressDescription;
}
