package dev.patika.veterinarymanagementsystem.dto.request.vaccine;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineSaveRequest {



    private String name;

    private String code;

    private LocalDate protectionStartDate;

    private LocalDate protectionFinishDate;
    private Long animal_id;
}
