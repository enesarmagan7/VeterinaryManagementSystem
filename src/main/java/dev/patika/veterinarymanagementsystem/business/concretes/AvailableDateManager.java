package dev.patika.veterinarymanagementsystem.business.concretes;

import dev.patika.veterinarymanagementsystem.business.abstracts.IAvailableDateService;
import dev.patika.veterinarymanagementsystem.business.abstracts.IDoctorService;
import dev.patika.veterinarymanagementsystem.core.exception.NotFoundException;
import dev.patika.veterinarymanagementsystem.dao.AvailableDateRepo;
import dev.patika.veterinarymanagementsystem.entities.AvailableDate;
import dev.patika.veterinarymanagementsystem.utilies.MSG;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AvailableDateManager implements IAvailableDateService {
    private  final AvailableDateRepo availableDateManager;


    public AvailableDateManager(AvailableDateRepo availableDateManager) {
        this.availableDateManager = availableDateManager;

    }

    @Override
    public AvailableDate save(AvailableDate availableDate) {


        return this.availableDateManager.save(availableDate);
    }

    @Override
    public AvailableDate get(Long id) {


        return this.availableDateManager.findById(id).orElseThrow(()->new RuntimeException(id + " id’li kayıt sistemde bulunamadı."));
    }

    @Override
    public Page<AvailableDate> cursor(int page, int pageSize) {
        Pageable pageable= PageRequest.of(page,pageSize);

        return this.availableDateManager.findAll(pageable);
    }

    @Override
    public AvailableDate update(AvailableDate availableDate) {

        this.get(availableDate.getId());
        return this.availableDateManager.save(availableDate);
    }

    @Override
    public boolean delete(Long id) {

       AvailableDate availableDate=this.get(id);
        this.availableDateManager.delete(availableDate);
        return true;
    }
}
