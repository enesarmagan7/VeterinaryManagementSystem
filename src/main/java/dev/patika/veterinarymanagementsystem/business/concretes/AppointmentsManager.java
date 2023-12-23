package dev.patika.veterinarymanagementsystem.business.concretes;



import dev.patika.veterinarymanagementsystem.business.abstracts.IDoctorService;
import dev.patika.veterinarymanagementsystem.business.abstracts.IAppointmentsService;
import dev.patika.veterinarymanagementsystem.core.exception.NotFoundException;
import dev.patika.veterinarymanagementsystem.dao.AppointmentsRepo;
import dev.patika.veterinarymanagementsystem.dao.AvailableDateRepo;
import dev.patika.veterinarymanagementsystem.entities.*;
import dev.patika.veterinarymanagementsystem.utilies.MSG;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AppointmentsManager implements IAppointmentsService {
    private final AppointmentsRepo appointmentsRepo;
    private final IDoctorService doctorService;
    private  final AvailableDateRepo availableDateRepo;

    public AppointmentsManager(AppointmentsRepo appointmentsRepo, IDoctorService doctorService, AvailableDateRepo availableDateRepo) {
        this.appointmentsRepo = appointmentsRepo;
        this.doctorService = doctorService;
        this.availableDateRepo = availableDateRepo;
    }


    @Override
    public Appointments save(Appointments appointments,Doctor doctor) {

        LocalDateTime appointmentDateTime = appointments.getAppointmentDateTime();
        // Dakika ve saniye bilgilerini kontrol et
        if (appointmentDateTime.getMinute() != 0 || appointmentDateTime.getSecond() != 0) {
            // Eğer dakika veya saniye bilgisi sıfır değilse, yani saat başı değilse uyarı ver
            throw new RuntimeException("Randevular sadece saat başlarında alınabilir!");
        }
        // Doktorun müsait günü var mı kontrolü
        if (!this.isAvailable(appointmentDateTime, doctor)) {
            throw new RuntimeException("Doktor bu tarihte çalışmamaktadır!");
        }

        // Girilen saatte başka bir randevusu var mı kontrolü
        if (this.hasAppointmentAtGivenTime(appointmentDateTime, doctor)) {
            throw new RuntimeException("Girilen saatte başka bir randevu mevcuttur!");
        }


        return this.appointmentsRepo.save(appointments);
    }
    public boolean hasAppointmentAtGivenTime(LocalDateTime date, Doctor doctor) {
        List<Appointments> appointments = this.appointmentsRepo.findByDoctorId(doctor.getId());

        return appointments.stream()
                .anyMatch(appointment -> appointment.getAppointmentDateTime().equals(date));
    }

    @Override
    public boolean isAvailable(LocalDateTime date, Doctor doctor) {
        LocalDate appointmentDate = date.toLocalDate();
        List<AvailableDate> availableDates = availableDateRepo.findByDoctorId(doctor.getId());

        // Doktorun müsait günleri arasında girilen tarih var mı kontrolü
        return availableDates.stream()
                .anyMatch(availableDate -> availableDate.getAvailableDate().equals(appointmentDate));
    }


    @Override
    public Appointments get(Long id) {

        return this.appointmentsRepo.findById(id).orElseThrow(()->new  RuntimeException(id + " id’li kayıt sistemde bulunamadı."));
    }

    @Override
    public Page<Appointments> cursor(int page, int pageSize) {
        Pageable pageable= PageRequest.of(page,pageSize);
        return this.appointmentsRepo.findAll(pageable);
    }
    @Override
    public List<Appointments> getAppointmentsByDoctorAndDateRange(Doctor doctor, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return appointmentsRepo.findByDoctorIdAndAppointmentDateTimeBetween(doctor.getId(), startDateTime, endDateTime);
    }

    @Override
    public List<Appointments> getAppointmentsByAnimalAndDateRange(Animal animal, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return appointmentsRepo.findByAnimalIdAndAppointmentDateTimeBetween(animal.getId(),startDateTime,endDateTime);
    }

    @Override
    public Appointments update(Appointments appointment) {

        return this.appointmentsRepo.save(appointment);
    }

    @Transactional
    @Override
    public boolean delete(Long id) {

        Appointments vacciner=this.get(id);
        this.appointmentsRepo.delete(vacciner);
        return true;
    }






}
