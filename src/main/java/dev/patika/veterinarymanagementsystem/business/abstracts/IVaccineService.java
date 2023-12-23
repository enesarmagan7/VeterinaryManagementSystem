package dev.patika.veterinarymanagementsystem.business.abstracts;


import dev.patika.veterinarymanagementsystem.entities.Vaccine;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface IVaccineService {
    Vaccine save(Vaccine vaccine);
    Vaccine get(Long id);
    Page<Vaccine> cursor(int page, int pageSize);
    Vaccine update(Vaccine vaccine);
    boolean delete(Long id);
    List<Vaccine> getVaccinesByAnimalId(Long animalId);
}
