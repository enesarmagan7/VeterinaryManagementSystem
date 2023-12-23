package dev.patika.veterinarymanagementsystem.api;

import dev.patika.veterinarymanagementsystem.business.abstracts.IAvailableDateService;
import dev.patika.veterinarymanagementsystem.business.abstracts.IDoctorService;
import dev.patika.veterinarymanagementsystem.core.config.modelMapper.IModelMapperService;
import dev.patika.veterinarymanagementsystem.core.config.result.Result;
import dev.patika.veterinarymanagementsystem.core.config.result.ResultData;
import dev.patika.veterinarymanagementsystem.dto.request.availabledate.AvailableDateSaveRequest;
import dev.patika.veterinarymanagementsystem.dto.request.availabledate.AvailableDateUpdateRequest;
import dev.patika.veterinarymanagementsystem.dto.response.availabledate.AvailableDateResponse;
import dev.patika.veterinarymanagementsystem.dto.response.CursorResponse;
import dev.patika.veterinarymanagementsystem.entities.AvailableDate;
import dev.patika.veterinarymanagementsystem.entities.Doctor;
import dev.patika.veterinarymanagementsystem.utilies.ResultHelper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/dates")
public class AvailableDateController {
    private final IAvailableDateService availableDateService;
    private final IDoctorService doctorService;
    private final IModelMapperService modelMapper;

    public AvailableDateController(IAvailableDateService availableDateService, IDoctorService doctorService, IModelMapperService modelMapper) {
        this.availableDateService = availableDateService;
        this.doctorService = doctorService;
        this.modelMapper = modelMapper;
    }



    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AvailableDateResponse> save(@Valid @RequestBody AvailableDateSaveRequest availableDateSaveRequest) {

       AvailableDate saveDates=this.modelMapper.forRequest().map(availableDateSaveRequest,AvailableDate.class);
        Doctor doctor=this.doctorService.get(availableDateSaveRequest.getDoctor_id());
        List<AvailableDate> dates=doctor.getAvailableDate();
        dates.add(saveDates);
        doctor.setAvailableDate(dates);
        saveDates.setDoctor(doctor);
        this.availableDateService.save(saveDates);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveDates, AvailableDateResponse.class));
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse> get(@PathVariable("id")Long id){
        AvailableDate availableDate=this.availableDateService.get(id);

        AvailableDateResponse availableDateResponse =this.modelMapper.forResponse().map(availableDate,AvailableDateResponse.class);
        return ResultHelper.succes(availableDateResponse);
    }
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AvailableDateResponse>> cursor(
            @RequestParam(name="page",required = false,defaultValue = "0") int page,
            @RequestParam(name="pageSize",required = false,defaultValue = "10") int pageSize
    ){
        Page<AvailableDate> datePage= this.availableDateService.cursor(page,pageSize);
        Page<AvailableDateResponse> availableDateResponsePage=datePage
                .map(date->this.modelMapper.forResponse().map(date,AvailableDateResponse.class));
        CursorResponse<AvailableDateResponse> cursor=new CursorResponse<>();
        cursor.setItems(availableDateResponsePage.getContent());
        cursor.setPageNumber(availableDateResponsePage.getNumber());
        cursor.setPageSize(availableDateResponsePage.getSize());
        cursor.setAtotalElements(availableDateResponsePage.getTotalElements());
        return ResultHelper.cursor(availableDateResponsePage);
    }
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse> update(@Valid @RequestBody AvailableDateUpdateRequest dateUpdateRequest){

        AvailableDate updateDate=this.modelMapper.forRequest().map(dateUpdateRequest,AvailableDate.class);
        this.availableDateService.update(updateDate);

        return ResultHelper.succes(this.modelMapper.forResponse().map(updateDate, AvailableDateResponse.class));

    }
    @DeleteMapping("/delete/{date_id}")
    @ResponseStatus(HttpStatus.OK)
    public Result deleteCustomer(@PathVariable("date_id") Long id){
        this.availableDateService.delete(id);
        return ResultHelper.ok();
    }

}
