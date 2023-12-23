package dev.patika.veterinarymanagementsystem.dto.response.vaccine;

import dev.patika.veterinarymanagementsystem.entities.Animal;
import lombok.*;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineResponse {
    private Long id;
    private String name;
    private String code;
    private LocalDate protectionStartDate;
    private LocalDate protectionFinishDate;
    private Long animalId;
}
