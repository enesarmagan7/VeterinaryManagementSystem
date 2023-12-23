package dev.patika.veterinarymanagementsystem.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name="vaccines")
public class Vaccine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="vaccine_id")
    private Long id;

    @Column(name="vaccine_name")
    private String name;

    @Column(name="vaccine_code")
    private String code;

    @Column(name="protectionStartDate")
    private LocalDate protectionStartDate;

    @Column(name="protectionFinishDate")
    private LocalDate protectionFinishDate;
    @ManyToOne
    @JoinColumn(name="animal_id")
    private Animal animal;


}
