package dev.patika.veterinarymanagementsystem.dao;

import dev.patika.veterinarymanagementsystem.entities.Animal;
import dev.patika.veterinarymanagementsystem.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalRepo extends JpaRepository<Animal,Long> {
    Optional<Animal> findByName(String name);

    List<Animal> findByVaccinesProtectionFinishDateBetween(LocalDate startDate, LocalDate endDate);
    List<Animal> findByCustomer_Id(Long customerId);


}
