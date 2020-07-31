package ru.pkozlov.msdeparture.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DepartureDto {
    private Long PK_id;
    private Long user_id;
    private Long departure_point_id;
    private Long arriving_point_id;
    private String type;
    private LocalDateTime departure_date;
    private Boolean arrived;
}

