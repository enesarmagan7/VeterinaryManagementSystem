package dev.patika.veterinarymanagementsystem.api;

import dev.patika.veterinarymanagementsystem.business.abstracts.IDoctorService;
import dev.patika.veterinarymanagementsystem.core.config.modelMapper.IModelMapperService;

import dev.patika.veterinarymanagementsystem.core.config.result.Result;
import dev.patika.veterinarymanagementsystem.core.config.result.ResultData;
import dev.patika.veterinarymanagementsystem.dto.request.doctor.DoctorSaveRequest;

import dev.patika.veterinarymanagementsystem.dto.request.doctor.DoctorUpdateRequest;
import dev.patika.veterinarymanagementsystem.dto.response.CursorResponse;

import dev.patika.veterinarymanagementsystem.dto.response.doctor.DoctorResponse;

import dev.patika.veterinarymanagementsystem.entities.Doctor;

import dev.patika.veterinarymanagementsystem.utilies.ResultHelper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/v1/doctors")
public class DoctorController {
    private final IDoctorService doctorService;
    private final IModelMapperService modelMapper;

    public DoctorController(IDoctorService doctorService, IModelMapperService modelMapper) {
        this.doctorService = doctorService;
        this.modelMapper = modelMapper;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<DoctorResponse> save(@Valid @RequestBody DoctorSaveRequest doctorSaveRequest){
        Doctor saveDoctor=this.modelMapper.forRequest().map(doctorSaveRequest, Doctor.class);
        this.doctorService.save(saveDoctor);

        return ResultHelper.created(this.modelMapper.forResponse().map(saveDoctor, DoctorResponse.class));

    }
    @GetMapping("/{doctorId}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<DoctorResponse> get(@PathVariable("doctorId")Long id){
        Doctor doctor=this.doctorService.get(id);
        DoctorResponse doctorResponse =this.modelMapper.forResponse().map(doctor,DoctorResponse.class);
        return ResultHelper.succes(doctorResponse);
    }
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<DoctorResponse>> cursor(
            @RequestParam(name="page",required = false,defaultValue = "0") int page,
            @RequestParam(name="pageSize",required = false,defaultValue = "10") int pageSize
    ){
        Page<Doctor> doctorPage= this.doctorService.cursor(page,pageSize);
        Page<DoctorResponse> doctorResponsePage=doctorPage
                .map(doctor->this.modelMapper.forResponse().map(doctor,DoctorResponse.class));
        CursorResponse<DoctorResponse> cursor=new CursorResponse<>();
        cursor.setItems(doctorResponsePage.getContent());
        cursor.setPageNumber(doctorResponsePage.getNumber());
        cursor.setPageSize(doctorResponsePage.getSize());
        cursor.setAtotalElements(doctorResponsePage.getTotalElements());
        return ResultHelper.cursor(doctorResponsePage);
    }
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<DoctorResponse> update(@Valid @RequestBody DoctorUpdateRequest doctorUpdateRequest){

        Doctor updateDoctor=this.modelMapper.forRequest().map(doctorUpdateRequest,Doctor.class);
        this.doctorService.update(updateDoctor);

        return ResultHelper.succes(this.modelMapper.forResponse().map(updateDoctor, DoctorResponse.class));

    }
    @DeleteMapping("/delete/{doctorId}")
    @ResponseStatus(HttpStatus.OK)
    public Result deleteCustomer(@PathVariable("doctorId") Long id){
        this.doctorService.delete(id);
        return ResultHelper.ok();
    }

}
