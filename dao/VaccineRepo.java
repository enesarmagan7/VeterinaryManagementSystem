package dev.patika.veterinarymanagementsystem.dao;


import dev.patika.veterinarymanagementsystem.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VaccineRepo extends JpaRepository<Vaccine,Long> {
   Vaccine findFirstByCodeAndNameAndAnimalIdOrderByProtectionFinishDateDesc(String code, String name,Long animalId);
    List<Vaccine> findByAnimalId(Long animal_id);
   // Vaccine findFirstByCodeAndNameAndAnimalIdOrderByProtectionFinishDateDesc(String code,String name,Long animalId);
}
