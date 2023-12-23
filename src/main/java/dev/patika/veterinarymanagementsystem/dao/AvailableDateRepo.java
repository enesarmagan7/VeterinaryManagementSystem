package dev.patika.veterinarymanagementsystem.dao;


import dev.patika.veterinarymanagementsystem.entities.AvailableDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvailableDateRepo extends JpaRepository<AvailableDate,Long> {
    List<AvailableDate> findByDoctorId(Long doctorId);
}
