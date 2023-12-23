package dev.patika.veterinarymanagementsystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import lombok.*;

import java.util.List;


@Entity
@Table(name="customers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="phone",unique = true)
    private String phone;

    @Column(name="mail",unique = true)
    @Email
    private String mail;

    @Column(name="address")
    private String address;

    @Column(name="city")
    private String city;

    @JsonIgnore
    @OneToMany(mappedBy = "customer",orphanRemoval = true)
    private List<Animal> animals;








}
