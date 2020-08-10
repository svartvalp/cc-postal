package com.crashcourse.msdeparture.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "departure", schema = "postal_schema")
public class Departure {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "departure_generator")
    @SequenceGenerator(name = "departure_generator", schema = "postal_schema", sequenceName = "departure_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "nearest_user_id")
    private Long nearestUser;
    @ManyToOne
    @JoinColumn(name = "departure_point_id")
    private Address departurePoint;
    @ManyToOne
    @JoinColumn(name = "arriving_point_id")
    private Address arrivingPoint;
    @Column(name = "type")
    private String type;
    @Column(name = "departure_date")
    private LocalDateTime departureDate;
    @Column(name = "arriving_date")
    private LocalDateTime arrivingDate;
    @Column(name = "arrived")
    private Boolean arrived;
    @Column(name = "weight")
    private Integer weight;
    @Column(name = "description")
    private String description;
}
