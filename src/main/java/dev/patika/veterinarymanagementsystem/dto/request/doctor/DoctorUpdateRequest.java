package dev.patika.veterinarymanagementsystem.dto.request.doctor;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorUpdateRequest {

    private Long id;

    private String name;


    private String phone;

    private String mail;

    private String address;

    private String city;

}
