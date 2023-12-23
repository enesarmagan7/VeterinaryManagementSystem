package dev.patika.veterinarymanagementsystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name="doctors")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="doctorId")
    private Long id;

    @Column(name="doctor_name")
    private String name;

    @Column(name="doctor_phone",unique = true)
    private String phone;

    @Column(name="doctor_mail",unique = true)
    private String mail;

    @Column(name="doctor_address")
    private String address;

    @Column(name="doctor_city")
    private String city;

    @ToString.Exclude
 @OneToMany(mappedBy = "doctor",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
  private List<AvailableDate> availableDate;

    @ToString.Exclude
    @OneToMany(mappedBy = "doctor",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE,orphanRemoval = true)
    private List<Appointments> appointments;






}
