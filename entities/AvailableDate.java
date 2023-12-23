package dev.patika.veterinarymanagementsystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="dates")

public class AvailableDate {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="id")
        private Long id;



        @Column(name="availableDate",unique = true)
        private LocalDate availableDate;


        @ManyToOne
        @JoinColumn(name="doctorId")
        private Doctor doctor;
}
