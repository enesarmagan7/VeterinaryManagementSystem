package dev.patika.veterinarymanagementsystem.api;

import dev.patika.veterinarymanagementsystem.business.abstracts.ICustomerService;
import dev.patika.veterinarymanagementsystem.core.config.modelMapper.IModelMapperService;
import dev.patika.veterinarymanagementsystem.core.config.result.Result;
import dev.patika.veterinarymanagementsystem.core.config.result.ResultData;
import dev.patika.veterinarymanagementsystem.dto.request.customer.CustomerSaveRequest;
import dev.patika.veterinarymanagementsystem.dto.request.customer.CustomerUpdateRequest;
import dev.patika.veterinarymanagementsystem.dto.response.CursorResponse;
import dev.patika.veterinarymanagementsystem.dto.response.animal.AnimalResponse;
import dev.patika.veterinarymanagementsystem.dto.response.customer.CustomerResponse;
import dev.patika.veterinarymanagementsystem.entities.Animal;
import dev.patika.veterinarymanagementsystem.entities.Customer;
import dev.patika.veterinarymanagementsystem.utilies.ResultHelper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {
    private final ICustomerService customerService;
    private final IModelMapperService modelMapper;

    public CustomerController(ICustomerService customerService, IModelMapperService modelmapper) {
        this.customerService = customerService;
        this.modelMapper = modelmapper;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<CustomerResponse> save(@Valid @RequestBody CustomerSaveRequest customerSaveRequest){
        Customer saveCustomer=this.modelMapper.forRequest().map(customerSaveRequest,Customer.class);
        this.customerService.save(saveCustomer);

       return ResultHelper.created(this.modelMapper.forResponse().map(saveCustomer, CustomerResponse.class));

    }
    @GetMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> getCustomerById(@PathVariable("customerId")Long id){
        Customer customer=this.customerService.get(id);
        CustomerResponse customerResponse =this.modelMapper.forResponse().map(customer,CustomerResponse.class);
        return ResultHelper.succes(customerResponse);
    }
    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> getCustomerByName(@PathVariable("name")String name){
        Customer customer=this.customerService.findByName(name);
        CustomerResponse customerResponse =this.modelMapper.forResponse().map(customer,CustomerResponse.class);
        return ResultHelper.succes(customerResponse);
    }
    //Müşteriye ait hayvanları kontrol etme
    @GetMapping("/animals/{customerId}")
    public ResultData<List<AnimalResponse>> getAnimalsByCustomerId(@PathVariable ("customerId")Long customerId) {
        List<Animal> animals = customerService.getAllAnimalsByCustomerId(customerId);

        List<AnimalResponse> animalResponses = animals.stream()
                .map(animal -> modelMapper.forResponse().map(animal, AnimalResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.succes(animalResponses);
    }


    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<CustomerResponse>> cursor(
@RequestParam(name="page",required = false,defaultValue = "0") int page,
@RequestParam(name="pageSize",required = false,defaultValue = "10") int pageSize
    ){
       Page<Customer> customerPage= this.customerService.cursor(page,pageSize);
       Page<CustomerResponse> customerResponsePage=customerPage
               .map(customer->this.modelMapper.forResponse().map(customer,CustomerResponse.class));
        CursorResponse<CustomerResponse> cursor=new CursorResponse<>();
        cursor.setItems(customerResponsePage.getContent());
        cursor.setPageNumber(customerResponsePage.getNumber());
        cursor.setPageSize(customerResponsePage.getSize());
        cursor.setAtotalElements(customerResponsePage.getTotalElements());
        return ResultHelper.cursor(customerResponsePage);
    }
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> update(@Valid @RequestBody CustomerUpdateRequest customerUpdateRequest){

        Customer updateCustomer=this.modelMapper.forRequest().map(customerUpdateRequest,Customer.class);
        this.customerService.update(updateCustomer);

        return ResultHelper.succes(this.modelMapper.forResponse().map(updateCustomer, CustomerResponse.class));

    }



    @DeleteMapping("/delete/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public Result deleteCustomer(@PathVariable("customerId") Long id){
        this.customerService.delete(id);
        return ResultHelper.ok();
    }




}
