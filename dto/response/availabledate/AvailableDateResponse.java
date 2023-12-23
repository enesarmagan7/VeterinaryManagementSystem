package dev.patika.veterinarymanagementsystem.dto.response.availabledate;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AvailableDateResponse {

    private Long id;


    private LocalDate availableDate;

    private Long doctorId;
}
