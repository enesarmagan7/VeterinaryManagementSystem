package dev.patika.veterinarymanagementsystem.dao;

import dev.patika.veterinarymanagementsystem.entities.Animal;
import dev.patika.veterinarymanagementsystem.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long> {
    Optional<Customer> findByName(String name);


}
