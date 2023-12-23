package dev.patika.veterinarymanagementsystem.dao;





import dev.patika.veterinarymanagementsystem.entities.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentsRepo extends JpaRepository<Appointments,Long> {

    List<Appointments> findByAnimal_Id(Long animal_id);
    // Doktor ID'sine ve tarih aralığına göre randevuları getir
    List<Appointments> findByDoctorIdAndAppointmentDateTimeBetween(
            Long doctorId,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime
    );
    List<Appointments> findByDoctorId(Long doctorId);

    List<Appointments> findByAnimalIdAndAppointmentDateTimeBetween(
            Long animalId,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime
    );
}
