package dev.patika.veterinarymanagementsystem.dto.request.appointments;



import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentsUpdateRequest {

    private Long id;

    private LocalDateTime appointmentDateTime;
    private Long animal_id;
    private Long doctorId;
}

