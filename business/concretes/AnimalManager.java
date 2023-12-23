package dev.patika.veterinarymanagementsystem.business.concretes;

import dev.patika.veterinarymanagementsystem.business.abstracts.IAnimalService;
import dev.patika.veterinarymanagementsystem.core.exception.NotFoundException;
import dev.patika.veterinarymanagementsystem.dao.AnimalRepo;
import dev.patika.veterinarymanagementsystem.entities.Animal;
import dev.patika.veterinarymanagementsystem.entities.Customer;
import dev.patika.veterinarymanagementsystem.utilies.MSG;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Data

public class AnimalManager implements IAnimalService {
    private final AnimalRepo animalRepo;

    public AnimalManager(AnimalRepo animalRepo) {
        this.animalRepo = animalRepo;
    }


    @Override
    @Transactional
    public Animal save(Animal animal) {

        return this.animalRepo.save(animal);
    }

    @Override
    public Animal update(Animal animal) {

        this.get((Long) animal.getId());
        return this.animalRepo.save(animal);
    }

    @Override
    public Animal get(Long id) {
        if (!animalRepo.findById(id).isPresent()) {
            throw new RuntimeException(id + " numaralı kayıt sistemde bulunamadı.");
        }

        return this.animalRepo.findById((id)).orElseThrow(()->new NotFoundException(MSG.NOT_FOUND));
    }

    @Override
    public Page<Animal> cursor(int page, int pageSize) {
        Pageable pageable= PageRequest.of(page,pageSize);
        return this.animalRepo.findAll(pageable);
    }




    @Override
    public Animal findByName(String name) {
        return this.animalRepo.findByName(name).orElseThrow(()->new RuntimeException(name + " isimli hayvan sistemde bulunamadı."));
    }

    @Override
    public boolean delete(Long id) {


        Animal animal=this.get(id);
         this.animalRepo.delete(animal);
         return true;
    }



    public List<Animal> getAnimalsByVaccinationRange(LocalDate startDate, LocalDate endDate) {
        return animalRepo.findByVaccinesProtectionFinishDateBetween(startDate, endDate);
    }
}
