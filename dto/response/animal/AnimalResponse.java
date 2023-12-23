package dev.patika.veterinarymanagementsystem.dto.response.animal;

import dev.patika.veterinarymanagementsystem.dto.response.customer.CustomerResponse;
import dev.patika.veterinarymanagementsystem.entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class AnimalResponse {
     private Long id;

    private String name;

    private String species;

    private String breed;

    private String gender;

    private String colour;

    private LocalDate dateOfBirth;
    private Long customerId;

}
