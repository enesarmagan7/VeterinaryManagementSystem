package dev.patika.veterinarymanagementsystem.business.abstracts;

import dev.patika.veterinarymanagementsystem.entities.Customer;
import dev.patika.veterinarymanagementsystem.entities.Doctor;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface IDoctorService {
    Doctor save(Doctor doctor);
    Doctor get(Long doctor_id);
    Page<Doctor> cursor(int page, int pageSize);
    Doctor update(Doctor doctor);
    boolean delete(Long doctor_id);
    boolean isAvailable(LocalDateTime date,Doctor doctor);



}
