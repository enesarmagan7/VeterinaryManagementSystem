package dev.patika.veterinarymanagementsystem.dto.response.appointments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.patika.veterinarymanagementsystem.dto.response.animal.AnimalResponse;
import dev.patika.veterinarymanagementsystem.dto.response.doctor.DoctorResponse;
import dev.patika.veterinarymanagementsystem.entities.Animal;
import dev.patika.veterinarymanagementsystem.entities.Doctor;
import lombok.*;

import java.time.LocalDateTime;


   @Data
    @AllArgsConstructor
    @NoArgsConstructor

    public class AppointmentsResponse {

        private Long id;
        private LocalDateTime appointmentDateTime;


        private AnimalResponse animalResponse; // Animal bilgileri
        private DoctorResponse doctorResponse; // Doctor bilgileri



}
