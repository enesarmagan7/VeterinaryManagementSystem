package dev.patika.veterinarymanagementsystem.business.concretes;

import dev.patika.veterinarymanagementsystem.business.abstracts.IVaccineService;
import dev.patika.veterinarymanagementsystem.core.exception.NotFoundException;
import dev.patika.veterinarymanagementsystem.dao.VaccineRepo;
import dev.patika.veterinarymanagementsystem.entities.Customer;
import dev.patika.veterinarymanagementsystem.entities.Vaccine;
import dev.patika.veterinarymanagementsystem.utilies.MSG;
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
public class VaccineManager implements IVaccineService {
    private final VaccineRepo vaccineRepo;

    public VaccineManager(VaccineRepo vaccineRepo) {
        this.vaccineRepo = vaccineRepo;
    }

    @Override
    public Vaccine save(Vaccine vaccine) {

        if(vaccine.getProtectionStartDate().isAfter(vaccine.getProtectionFinishDate())){
            throw new RuntimeException("Aşı koruma başlangıç tarihi, aşı koruma bitiş tarihinden daha önce olmalıdır");
        }



        validateVaccine(vaccine);


        return this.vaccineRepo.save(vaccine);
    }

    @Override
    public Vaccine get(Long id) {

        return this.vaccineRepo.findById(id).orElseThrow(()->new RuntimeException(id + " id’li kayıt sistemde bulunamadı."));
    }

    @Override
    public Page<Vaccine> cursor(int page, int pageSize) {
        Pageable pageable= PageRequest.of(page,pageSize);

        return this.vaccineRepo.findAll(pageable);
    }

    @Override
    public Vaccine update(Vaccine vaccine) {



        this.get((Long) vaccine.getId());
        return this.vaccineRepo.save(vaccine);
    }
    private void validateVaccine(Vaccine vaccine) {

        if(vaccine.getProtectionStartDate().isAfter(vaccine.getProtectionFinishDate())){
            throw new RuntimeException("Aşı koruma başlangıç tarihi, aşı koruma bitiş tarihinden daha önce olmalıdır");
        }
        // Aşı kodu ve bitiş tarihi kontrolü
        Vaccine existingVaccine = vaccineRepo.findFirstByCodeAndNameAndAnimalIdOrderByProtectionFinishDateDesc(vaccine.getCode(), vaccine.getName(),vaccine.getAnimal().getId());
        if (existingVaccine != null) {
            if(existingVaccine.getProtectionStartDate().isEqual(vaccine.getProtectionStartDate()) &&existingVaccine.getProtectionFinishDate().isEqual(vaccine.getProtectionFinishDate())){
                throw new RuntimeException("Daha önce aynı tarihlerde aşı kayıdı bulunamktadır..");

            }
            if(vaccine.getProtectionStartDate().isBefore(existingVaccine.getProtectionFinishDate())){
                throw new RuntimeException("Aynı tip aşının bitiş tarihi daha gelmemiş bir kaydı bulunmaktadır.");
            }
        }
    }

    @Override
    public boolean delete(Long id) {

        Vaccine vacciner=this.get(id);
        this.vaccineRepo.delete(vacciner);
        return true;
    }
    @Override
    public List<Vaccine> getVaccinesByAnimalId(Long animalId) {
        return vaccineRepo.findByAnimalId(animalId);
    }

}
