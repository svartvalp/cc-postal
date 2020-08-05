package com.crashcourse.msdeparture.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address", schema = "postal_schema")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_generator")
    @SequenceGenerator(name = "address_generator", schema = "postal_schema", sequenceName = "address_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = " latitude")
    private Double latitude;
    @Column(name = "address")
    private String address;
}
