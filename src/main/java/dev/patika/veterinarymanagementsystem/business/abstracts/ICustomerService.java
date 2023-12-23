package dev.patika.veterinarymanagementsystem.business.abstracts;

import dev.patika.veterinarymanagementsystem.entities.Animal;
import dev.patika.veterinarymanagementsystem.entities.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICustomerService {
    Customer save(Customer customer);
    Customer get(Long customer_id);
    Customer findByName(String name);
    Page<Customer> cursor(int page,int pageSize);
    Customer update(Customer customer);
    List<Animal> getAllAnimalsByCustomerId(Long customer_id) ;

boolean delete(Long id);
}
