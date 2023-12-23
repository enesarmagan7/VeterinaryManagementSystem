package dev.patika.veterinarymanagementsystem.dto.request.availabledate;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDateUpdateRequest {

    private LocalDate availableDate;

    private Long doctor_id;
    public LocalDate getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(LocalDate avaibleDate) {
        this.availableDate = avaibleDate;
    }
}
