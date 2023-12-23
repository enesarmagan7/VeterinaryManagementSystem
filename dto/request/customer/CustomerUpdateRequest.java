package dev.patika.veterinarymanagementsystem.dto.request.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateRequest {

    private int customerId;


    private String name;


    private String phone;


    @Email
    private String mail;


    private String address;


    private String city;

     private Long animal_id;

}
