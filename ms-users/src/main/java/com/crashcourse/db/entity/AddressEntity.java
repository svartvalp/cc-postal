package com.crashcourse.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(
        name = "address"
)
@Getter
@Setter
public class AddressEntity {
    @Id
    @Column(
            name = "id"
    )
    @SequenceGenerator(
            name = "ADDRESS_ID_GENERATOR",
            sequenceName = "address_seq",
            schema = "postal_schema",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ADDRESS_ID_GENERATOR"
    )
    private Integer id;
    @Column(
            name = "longitude"
    )
    private Double longitude;
    @Column(
            name = "latitude"
    )
    private Double latitude;
    @Column(
            name = "address"
    )
    private String address;
}