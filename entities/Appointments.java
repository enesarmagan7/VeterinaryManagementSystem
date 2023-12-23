package dev.patika.veterinarymanagementsystem.entities;




import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="appointments")
public class Appointments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="appointment_id")
    private Long id;

    @Column(name="appointmentDate")
    private LocalDateTime appointmentDateTime;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="animal_id")
    private Animal animal;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="doctorId")
    private Doctor doctor;


}
