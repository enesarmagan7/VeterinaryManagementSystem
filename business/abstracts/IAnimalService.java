package dev.patika.veterinarymanagementsystem.business.abstracts;

import dev.patika.veterinarymanagementsystem.entities.Animal;
import dev.patika.veterinarymanagementsystem.entities.Customer;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IAnimalService {

    Animal save(Animal animal);
    Animal update(Animal animal);
    Animal get(Long id);
    Page<Animal> cursor(int page, int pageSize);


    Animal findByName(String name);
    // aşı koruyuculuk tarihi bu aralıkta olan hayvanların listesini geri döndürme
    List<Animal> getAnimalsByVaccinationRange(LocalDate startDate, LocalDate endDate);
    boolean delete(Long id);

}

