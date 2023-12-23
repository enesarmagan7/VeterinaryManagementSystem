package dev.patika.veterinarymanagementsystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="animals")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="animal_id")
    private Long id;

    @Column(name="name",unique = true)
    private String name;

    @Column(name="species")
    private String species;
    @Column(name="breed")
    private String breed;
    @Column(name="gender")
    private String gender;
    @Column(name="colour")
    private String colour;
    @Column(name="dateOfBirth")
    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "customer_id")

    private Customer customer;


    @OneToMany(mappedBy = "animal",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Appointments> appointments;

    @OneToMany(mappedBy = "animal",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Vaccine> vaccines;






}
