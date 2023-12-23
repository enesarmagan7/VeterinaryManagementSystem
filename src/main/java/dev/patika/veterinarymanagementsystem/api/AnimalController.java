package dev.patika.veterinarymanagementsystem.api;

import dev.patika.veterinarymanagementsystem.business.abstracts.IAnimalService;
import dev.patika.veterinarymanagementsystem.business.abstracts.ICustomerService;
import dev.patika.veterinarymanagementsystem.core.config.modelMapper.IModelMapperService;
import dev.patika.veterinarymanagementsystem.core.config.result.Result;
import dev.patika.veterinarymanagementsystem.core.config.result.ResultData;
import dev.patika.veterinarymanagementsystem.core.exception.NotFoundException;
import dev.patika.veterinarymanagementsystem.dto.request.animal.AnimalSaveRequest;
import dev.patika.veterinarymanagementsystem.dto.request.animal.AnimalUpdateRequest;

import dev.patika.veterinarymanagementsystem.dto.response.CursorResponse;
import dev.patika.veterinarymanagementsystem.dto.response.animal.AnimalResponse;

import dev.patika.veterinarymanagementsystem.dto.response.customer.CustomerResponse;
import dev.patika.veterinarymanagementsystem.entities.Animal;

import dev.patika.veterinarymanagementsystem.entities.Customer;
import dev.patika.veterinarymanagementsystem.utilies.ResultHelper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/animals")
public class AnimalController {
    private final IAnimalService animalService;
    private final ICustomerService customerService;
    private final IModelMapperService modelMapper;

    public AnimalController(IAnimalService animalService, ICustomerService customerService, IModelMapperService modelMapperService) {
        this.animalService = animalService;
        this.customerService = customerService;
        this.modelMapper = modelMapperService;
    }
    //Animal ekleme
    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AnimalResponse> save(@Valid @RequestBody AnimalSaveRequest animalSaveRequest){

        Animal saveAnimal=this.modelMapper.forRequest().map(animalSaveRequest,Animal.class);
        Customer customer=this.customerService.get(animalSaveRequest.getCustomer_Id());
        CustomerResponse customerResponse=this.modelMapper.forResponse().map(customer,CustomerResponse.class);
        saveAnimal.setCustomer(customer);



        this.animalService.save(saveAnimal);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveAnimal,AnimalResponse.class));

    }
    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> update(@Valid @RequestBody AnimalUpdateRequest animaLUpdateRequest){
        Animal updateAnimal=this.modelMapper.forRequest().map(animaLUpdateRequest,Animal.class);
        this.animalService.update( updateAnimal);
        return ResultHelper.created(this.modelMapper.forResponse().map(updateAnimal, AnimalResponse.class));

    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> get(@PathVariable("id")Long id){
        Animal animal=this.animalService.get(id);
        AnimalResponse animalResponse =this.modelMapper.forResponse().map(animal,AnimalResponse.class);
        return ResultHelper.succes(animalResponse);
    }
    @GetMapping("name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> getByName(@PathVariable("name")String name){
        Animal animal=this.animalService.findByName(name);
        AnimalResponse animalResponse =this.modelMapper.forResponse().map(animal,AnimalResponse.class);
        return ResultHelper.succes(animalResponse);
    }


    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AnimalResponse>> cursor(
            @RequestParam(name="page",required = false,defaultValue = "0") int page,
            @RequestParam(name="pageSize",required = false,defaultValue = "10") int pageSize
    ){
        Page<Animal> animalPage= this.animalService.cursor(page,pageSize);
        Page<AnimalResponse> animalResponsesPage=animalPage
                .map(animal->this.modelMapper.forResponse().map(animalPage,AnimalResponse.class));
        CursorResponse<AnimalResponse> cursor=new CursorResponse<>();
        cursor.setItems(animalResponsesPage.getContent());
        cursor.setPageNumber(animalResponsesPage.getNumber());
        cursor.setPageSize(animalResponsesPage.getSize());
        cursor.setAtotalElements(animalResponsesPage.getTotalElements());
        return ResultHelper.cursor(animalResponsesPage);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id){
        this.animalService.delete(id);
        return ResultHelper.ok();
    }



}
