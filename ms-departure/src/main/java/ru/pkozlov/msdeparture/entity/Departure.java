package ru.pkozlov.msdeparture.entity;

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
    @Column(name = "PK_id")
    private Long PK_id;
    @Column(name = "user_id")
    private Long user_id;
    @Column(name = "departure_point_id")
    private Long departure_point_id;
    @Column(name = "arriving_point_id")
    private Long arriving_point_id;
    @Column(name = "type")
    private String type;
    @Column(name = "departure_date")
    private LocalDateTime departure_date;
    @Column(name = "arrived")
    private Boolean arrived;


}
