package dev.patika.veterinarymanagementsystem.business.concretes;

import dev.patika.veterinarymanagementsystem.business.abstracts.IDoctorService;
import dev.patika.veterinarymanagementsystem.core.exception.NotFoundException;
import dev.patika.veterinarymanagementsystem.dao.DoctorRepo;

import dev.patika.veterinarymanagementsystem.entities.Doctor;
import dev.patika.veterinarymanagementsystem.utilies.MSG;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Transactional
public class DoctorManager implements IDoctorService {
    private final DoctorRepo doctorRepo;

    public DoctorManager(DoctorRepo doctorRepo) {
        this.doctorRepo = doctorRepo;
    }

    @Override
    public Doctor save(Doctor doctor) {

        return this.doctorRepo.save(doctor);
    }

    @Override
    public Doctor get(Long id) {

        return this.doctorRepo.findById(id).orElseThrow(()->new RuntimeException(id + " id’li kayıt sistemde bulunamadı."));
    }

    @Override
    public Page<Doctor> cursor(int page, int pageSize) {
        Pageable pageable= PageRequest.of(page,pageSize);

        return this.doctorRepo.findAll(pageable);
    }

    @Override
    public Doctor update(Doctor doctor) {

        this.get((Long) doctor.getId());
        return this.doctorRepo.save(doctor);
    }

    @Override
    public boolean delete(Long id) {

        Doctor doctor=this.get(id);
        this.doctorRepo.delete(doctor);
        return true;
    }

    @Override
    public boolean isAvailable(LocalDateTime date, Doctor doctor) {

            LocalDate appointmentDate = date.toLocalDate();
            return doctor.getAvailableDate().contains(appointmentDate);

    }




}
