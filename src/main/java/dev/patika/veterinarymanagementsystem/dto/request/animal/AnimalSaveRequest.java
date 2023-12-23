package dev.patika.veterinarymanagementsystem.dto.request.animal;


import dev.patika.veterinarymanagementsystem.entities.Customer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalSaveRequest {
    private Long id;
    private String name;

    private String species;

    private String breed;

    private String gender;

    private String colour;
    private LocalDate dateOfBirth;
    private Long customer_Id;

}
