package dev.patika.veterinarymanagementsystem.business.concretes;

import dev.patika.veterinarymanagementsystem.business.abstracts.ICustomerService;
import dev.patika.veterinarymanagementsystem.core.exception.NotFoundException;
import dev.patika.veterinarymanagementsystem.dao.AnimalRepo;
import dev.patika.veterinarymanagementsystem.dao.CustomerRepo;
import dev.patika.veterinarymanagementsystem.entities.Animal;
import dev.patika.veterinarymanagementsystem.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerManager implements ICustomerService {
    private  final CustomerRepo customerRepo;
    private final AnimalRepo animalRepo;
    public CustomerManager(CustomerRepo customer, AnimalRepo animalRepo){
     this.customerRepo=customer;
        this.animalRepo = animalRepo;
    }
    @Override
    public Customer save(Customer customer) {


        return this.customerRepo.save(customer);
    }

    @Override
    public Customer get(Long id) {

        return this.customerRepo.findById(id).orElseThrow(()->new NotFoundException(id + " id’li kayıt sistemde bulunamadı."));
    }

    @Override
    public Customer findByName(String name) {
        return this.customerRepo.findByName(name)
                .orElseThrow(() -> new RuntimeException(name + " isimli kayıt sistemde bulunamadı."));
    }

    @Override
    public Page<Customer> cursor(int page, int pageSize) {
        Pageable pageable= PageRequest.of(page,pageSize);

        return this.customerRepo.findAll(pageable);
    }

    @Override
    public Customer update(Customer customer) {

        this.get((Long) customer.getId());
        return this.customerRepo.save(customer);
    }

    @Override
    public List<Animal> getAllAnimalsByCustomerId(Long customerId) {
        Customer customer = this.customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException( customerId+"id'li Müşteri Bulunamadı" ));
      return this.animalRepo.findByCustomer_Id(customerId);
    }

    @Override
    public boolean delete(Long id) {

        Customer customer=this.get(id);
        this.customerRepo.delete(customer);
        return true;
    }
}
