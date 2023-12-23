package dev.patika.veterinarymanagementsystem.api;

import dev.patika.veterinarymanagementsystem.business.abstracts.IAnimalService;
import dev.patika.veterinarymanagementsystem.business.abstracts.IVaccineService;
import dev.patika.veterinarymanagementsystem.core.config.modelMapper.IModelMapperService;
import dev.patika.veterinarymanagementsystem.core.config.result.Result;
import dev.patika.veterinarymanagementsystem.core.config.result.ResultData;

import dev.patika.veterinarymanagementsystem.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.veterinarymanagementsystem.dto.request.vaccine.VaccineUpdateRequest;
import dev.patika.veterinarymanagementsystem.dto.response.CursorResponse;

import dev.patika.veterinarymanagementsystem.dto.response.animal.AnimalResponse;
import dev.patika.veterinarymanagementsystem.dto.response.vaccine.VaccineResponse;

import dev.patika.veterinarymanagementsystem.entities.Animal;
import dev.patika.veterinarymanagementsystem.entities.Customer;
import dev.patika.veterinarymanagementsystem.entities.Vaccine;
import dev.patika.veterinarymanagementsystem.utilies.ResultHelper;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/vaccines")
public class VaccineController {
    private final IVaccineService vaccineService;
    private final IAnimalService animalService;
    private final IModelMapperService modelMapper;

    public VaccineController(IVaccineService vaccineService, IAnimalService animalService, IModelMapperService modelMapper) {
        this.vaccineService = vaccineService;
        this.animalService = animalService;
        this.modelMapper = modelMapper;
    }
    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<VaccineResponse> save(@Valid @RequestBody VaccineSaveRequest vaccineSaveRequest) {

        Vaccine saveVaccine=this.modelMapper.forRequest().map(vaccineSaveRequest,Vaccine.class);
        Animal animal=this.animalService.get(vaccineSaveRequest.getAnimal_id());



        saveVaccine.setAnimal(animal);

        this.vaccineService.save(saveVaccine);

        return ResultHelper.created(this.modelMapper.forResponse().map(saveVaccine, VaccineResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> get(@PathVariable("id")Long id){
        Vaccine vaccine=this.vaccineService.get(id);
        VaccineResponse vaccineResponse =this.modelMapper.forResponse().map(vaccine,VaccineResponse.class);
        return ResultHelper.succes(vaccineResponse);
    }
    // hayvana ait tüm aşıları getirme
    @GetMapping("/animal/{animal_id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> getVaccinesByAnimalId(@PathVariable("animal_id") Long animalId) {
        List<Vaccine> vaccines = vaccineService.getVaccinesByAnimalId(animalId);

        List<VaccineResponse> vaccineResponses = vaccines.stream()
                .map(vaccine -> modelMapper.forResponse().map(vaccine, VaccineResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.succes(vaccineResponses);
    }
  // aşı koruyuculuk tarihi bu aralıkta olan hayvanların listesini geri döndüren API
    @GetMapping("/animals-in-vaccination-range")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> getAnimalsByVaccinationRange(
            @RequestParam(name = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        List<Animal> animals = animalService.getAnimalsByVaccinationRange(startDate, endDate);

        List<AnimalResponse> animalResponses = animals.stream()
                .map(animal -> modelMapper.forResponse().map(animal, AnimalResponse.class))
                .collect(Collectors.toList());

        return ResultHelper.succes(animalResponses);
    }
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<VaccineResponse>> cursor(
            @RequestParam(name="page",required = false,defaultValue = "0") int page,
            @RequestParam(name="pageSize",required = false,defaultValue = "2") int pageSize
    ){
        Page<Vaccine> vaccinePage= this.vaccineService.cursor(page,pageSize);
        Page<VaccineResponse> vaccineResponsePage=vaccinePage
                .map(vaccine->this.modelMapper.forResponse().map(vaccine,VaccineResponse.class));
        CursorResponse<VaccineResponse> cursor=new CursorResponse<>();
        cursor.setItems(vaccineResponsePage.getContent());
        cursor.setPageNumber(vaccineResponsePage.getNumber());
        cursor.setPageSize(vaccineResponsePage.getSize());
        cursor.setAtotalElements(vaccineResponsePage.getTotalElements());
        return ResultHelper.cursor(vaccineResponsePage);
    }
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> update(@Valid @RequestBody VaccineUpdateRequest vaccineUpdateRequest){

        Vaccine updateVaccine=this.modelMapper.forRequest().map(vaccineUpdateRequest,Vaccine.class);
        this.vaccineService.update(updateVaccine);

        return ResultHelper.succes(this.modelMapper.forResponse().map(updateVaccine, VaccineResponse.class));

    }
    @DeleteMapping("/delete/{vaccineId}")
    @ResponseStatus(HttpStatus.OK)
    public Result deleteCustomer(@PathVariable("vaccineId") Long id){
        this.vaccineService.delete(id);
        return ResultHelper.ok();
    }

}
