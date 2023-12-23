package dev.patika.veterinarymanagementsystem.business.abstracts;

import dev.patika.veterinarymanagementsystem.entities.AvailableDate;
import dev.patika.veterinarymanagementsystem.entities.Vaccine;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IAvailableDateService {
    AvailableDate save(AvailableDate availableDate);
    AvailableDate get(Long id);
    Page<AvailableDate> cursor(int page, int pageSize);
    AvailableDate update(AvailableDate availableDate);
    boolean delete(Long id);

}
