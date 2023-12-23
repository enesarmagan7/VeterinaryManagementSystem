package dev.patika.veterinarymanagementsystem.api;



import dev.patika.veterinarymanagementsystem.business.abstracts.IAnimalService;
import dev.patika.veterinarymanagementsystem.business.abstracts.IDoctorService;
import dev.patika.veterinarymanagementsystem.business.abstracts.IAppointmentsService;
import dev.patika.veterinarymanagementsystem.core.config.modelMapper.IModelMapperService;
import dev.patika.veterinarymanagementsystem.core.config.result.Result;
import dev.patika.veterinarymanagementsystem.core.config.result.ResultData;

import dev.patika.veterinarymanagementsystem.dto.request.appointments.AppointmentsSaveRequest;
import dev.patika.veterinarymanagementsystem.dto.request.appointments.AppointmentsUpdateRequest;
import dev.patika.veterinarymanagementsystem.dto.response.CursorResponse;

import dev.patika.veterinarymanagementsystem.dto.response.animal.AnimalResponse;
import dev.patika.veterinarymanagementsystem.dto.response.appointments.AppointmentsResponse;
import dev.patika.veterinarymanagementsystem.dto.response.doctor.DoctorResponse;
import dev.patika.veterinarymanagementsystem.entities.Animal;
import dev.patika.veterinarymanagementsystem.entities.Doctor;
import dev.patika.veterinarymanagementsystem.entities.Appointments;
import dev.patika.veterinarymanagementsystem.utilies.ResultHelper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/appointments")
public class AppointmentsController {
    private final IAppointmentsService appointmentsService;
    private final IAnimalService animalService;
    private final IModelMapperService modelMapper;
    private final IDoctorService doctorService;

    public AppointmentsController(IAppointmentsService appointmentsService, IAnimalService animalService, IModelMapperService modelMapper, IDoctorService doctorService) {
        this.appointmentsService = appointmentsService;
        this.animalService = animalService;
        this.modelMapper = modelMapper;
        this.doctorService = doctorService;
    }


    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AppointmentsResponse> save(@Valid @RequestBody AppointmentsSaveRequest appointmentSaveRequest) {

        // Talep üzerinden gelen veriyi Appointments sınıfına dönüştür
        Appointments saveAppointment = this.modelMapper.forRequest().map(appointmentSaveRequest, Appointments.class);

        // Animal ve Doctor nesnelerini ID'leri kullanarak al
        Animal animal = this.animalService.get(appointmentSaveRequest.getAnimal_id());
        Doctor doctor = this.doctorService.get(appointmentSaveRequest.getDoctorId());

        AnimalResponse animalResponse=this.modelMapper.forResponse().map(animal,AnimalResponse.class);
        DoctorResponse doctorResponse=this.modelMapper.forResponse().map(doctor,DoctorResponse.class);


        // Yeni randevunun Animal ve Doctor bilgilerini set et
        saveAppointment.setAnimal(animal);
        saveAppointment.setDoctor(doctor);

        // Yeni randevuyu kaydet ve oluşan nesneyi Response sınıfına dönüştürerek döndür
        this.appointmentsService.save(saveAppointment, doctor);
        AppointmentsResponse response = this.modelMapper.forResponse().map(saveAppointment, AppointmentsResponse.class);

        response.setAnimalResponse(animalResponse);
        response.setDoctorResponse(doctorResponse);
        return ResultHelper.created(response);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentsResponse> get(@PathVariable("id")Long id){
        Appointments appointment=this.appointmentsService.get(id);
        AppointmentsResponse vaccineResponse =this.modelMapper.forResponse().map(appointment,AppointmentsResponse.class);
        return ResultHelper.succes(vaccineResponse);
    }
    //Doktora ve tarih aralığına göre filtreleme
    @GetMapping("/by-doctor-and-date-range")
    public ResultData<List<AppointmentsResponse>>  getAppointmentsByDoctorAndDateRange(
            @RequestParam Long doctorId,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end           )

    {


        Doctor doctor = this.doctorService.get(doctorId);

        List<Appointments> appointments=this.appointmentsService.getAppointmentsByDoctorAndDateRange(doctor, start,end);
        List<AppointmentsResponse> appointmentResponses=appointments.stream()
                .map(appointment -> {
                    AppointmentsResponse response = new AppointmentsResponse();
                    response.setId(appointment.getId());

                    response.setAppointmentDateTime(appointment.getAppointmentDateTime());
                    DoctorResponse doctorResponse=this.modelMapper.forResponse().map(appointment.getDoctor(),DoctorResponse.class);
                    AnimalResponse animalResponse=this.modelMapper.forResponse().map(appointment.getAnimal(),AnimalResponse.class);
                    response.setDoctorResponse(doctorResponse);
                    response.setAnimalResponse(animalResponse);


                    return response;
                })
                .collect(Collectors.toList());
        return ResultHelper.succes(appointmentResponses);
    }

    // Hayvan a göre ve tarih aralığına göre randevuları getir


    @GetMapping("/by-animal-and-date-range")
    public ResultData<List<AppointmentsResponse>>  getAppointmentsByAnimalAndDateRange(
            @RequestParam Long animal_id,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end           )

    {


        Animal animal = this.animalService.get(animal_id);
        List<Appointments> appointments=this.appointmentsService.getAppointmentsByAnimalAndDateRange(animal, start,end);
        List<AppointmentsResponse> appointmentResponses=appointments.stream()
                .map(appointment -> {
                    AppointmentsResponse response = new AppointmentsResponse();
                    response.setId(appointment.getId());


                    response.setAppointmentDateTime(appointment.getAppointmentDateTime());
                    DoctorResponse doctorResponse=this.modelMapper.forResponse().map(appointment.getDoctor(),DoctorResponse.class);
                    AnimalResponse animalResponse=this.modelMapper.forResponse().map(appointment.getAnimal(),AnimalResponse.class);
                    response.setDoctorResponse(doctorResponse);
                    response.setAnimalResponse(animalResponse);

                    return response;
                })
                .collect(Collectors.toList());

        return ResultHelper.succes(appointmentResponses);
    }

  //Sayfalama
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AppointmentsResponse>> cursor(
            @RequestParam(name="page",required = false,defaultValue = "0") int page,
            @RequestParam(name="pageSize",required = false,defaultValue = "10") int pageSize
    ){
        Page<Appointments> appointmentDatePage= this.appointmentsService.cursor(page,pageSize);
        Page<AppointmentsResponse> appointmentResponsesPage=appointmentDatePage
                .map(appointment->this.modelMapper.forResponse().map(appointment,AppointmentsResponse.class));
        CursorResponse<AppointmentsResponse> cursor=new CursorResponse<>();
        cursor.setItems(appointmentResponsesPage.getContent());
        cursor.setPageNumber(appointmentResponsesPage.getNumber());
        cursor.setPageSize(appointmentResponsesPage.getSize());
        cursor.setAtotalElements(appointmentResponsesPage.getTotalElements());
        return ResultHelper.cursor(appointmentResponsesPage);
    }


    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentsResponse> update(@Valid @RequestBody AppointmentsUpdateRequest vaccineUpdateRequest){

        Appointments updateVaccine=this.modelMapper.forRequest().map(vaccineUpdateRequest, Appointments.class);
        this.appointmentsService.update(updateVaccine);

        return ResultHelper.succes(this.modelMapper.forResponse().map(updateVaccine, AppointmentsResponse.class));

    }
    @DeleteMapping("/delete/{appointment_id}")
    @ResponseStatus(HttpStatus.OK)
    public Result deleteCustomer(@PathVariable("appointment_id") Long id){
        this.animalService.delete(id);
        return ResultHelper.ok();
    }

}
