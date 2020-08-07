package com.crashcourse.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users", schema = "postal_schema")
@Getter
@Setter
public class UserEntity {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "USER_ID_GENERATOR", sequenceName = "users_seq", schema = "postal_schema", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ID_GENERATOR")
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "login")
    private String login;
    @Column(name = "passport_number")
    private String passportNumber;
    @Column(name = "password")
    private String password;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_addresses",
            schema = "postal_schema",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "address_id")}
    )
    private AddressEntity address;
}
