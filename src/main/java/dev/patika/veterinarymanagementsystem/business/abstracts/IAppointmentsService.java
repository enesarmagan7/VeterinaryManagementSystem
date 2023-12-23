package dev.patika.veterinarymanagementsystem.business.abstracts;




import dev.patika.veterinarymanagementsystem.entities.Animal;
import dev.patika.veterinarymanagementsystem.entities.Doctor;
import dev.patika.veterinarymanagementsystem.entities.Appointments;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentsService {
    Appointments save(Appointments appointments,Doctor doctor);
    Appointments get(Long id);
    Page<Appointments> cursor(int page, int pageSize);
    Appointments update(Appointments appointments);
    boolean delete(Long id);

    public boolean isAvailable(LocalDateTime date,Doctor doctor);
    boolean hasAppointmentAtGivenTime(LocalDateTime date, Doctor doctor);
    List<Appointments> getAppointmentsByDoctorAndDateRange(Doctor doctor, LocalDateTime startDateTime, LocalDateTime endDateTime);
    List<Appointments> getAppointmentsByAnimalAndDateRange(Animal animal, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
