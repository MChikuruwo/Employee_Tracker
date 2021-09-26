package com.employeetracker.EmployeeTrackerAPI.controller;


import com.employeetracker.EmployeeTrackerAPI.api.config.ApiResponse;
import com.employeetracker.EmployeeTrackerAPI.dto.AddDelegationOfDutyDto;
import com.employeetracker.EmployeeTrackerAPI.dto.UpdateDelegationOfDutyDto;
import com.employeetracker.EmployeeTrackerAPI.models.DelegationOfDuty;
import com.employeetracker.EmployeeTrackerAPI.models.Employee;
import com.employeetracker.EmployeeTrackerAPI.service.iface.DelegationOfDutyService;
import com.employeetracker.EmployeeTrackerAPI.service.iface.EmailService;
import com.employeetracker.EmployeeTrackerAPI.service.iface.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/duties", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "/api/v1/duties", produces = MediaType.APPLICATION_JSON_VALUE)
public class DelegationOfDutyController {

    private final DelegationOfDutyService delegationOfDutyService;
    private final EmployeeService employeeService;
    private final EmailService emailService;
    private final ModelMapper modelMapper;

    @Autowired
    public DelegationOfDutyController(DelegationOfDutyService delegationOfDutyService, EmployeeService employeeService,EmailService emailService,ModelMapper modelMapper) {
        this.delegationOfDutyService = delegationOfDutyService;
        this.employeeService = employeeService;
        this.emailService = emailService;
        this.modelMapper = modelMapper;
    }
    @GetMapping("/")
    @ApiOperation(value = "Get all delegated duties", response = ApiResponse.class)
    public ApiResponse getAllDelegationOfDuties(){
        return new ApiResponse(200, "SUCCESS", delegationOfDutyService.getAll());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a delegation of duty  record by its id. Takes id as a path variable",
            response = ApiResponse.class)
    public ApiResponse getDelegationOfDutyById(@PathVariable("id") Long id){
        return new ApiResponse(200, "SUCCESS", delegationOfDutyService.getOne(id));
    }

    @GetMapping("/by-assigning-manager/{id}")
    @ApiOperation(value = "Get a delegation of duty  record by the assigning manager. Takes id as a path variable",
            response = ApiResponse.class)
    public ApiResponse getDelegationOfDutyByAssigningManager(@PathVariable("id") Integer managerId){
        Employee manager = employeeService.getOne(managerId);
        return new ApiResponse(200, "SUCCESS", delegationOfDutyService.findAllByAssigningManager(manager));
    }

    @GetMapping("/by-assigned-employee/{id}")
    @ApiOperation(value = "Get a delegation of duty record by the transferring employee. Takes id as a path variable",
            response = ApiResponse.class)
    public ApiResponse getDelegationOfDutyByEmployee(@PathVariable("id") Integer employeeId){
        Employee employee = employeeService.getOne(employeeId);
        return new ApiResponse(200, "SUCCESS", delegationOfDutyService.findAllByAssignedEmployee(employee));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete a delegation of duty  record. Takes id as a path variable",
            response = ApiResponse.class)
    public ApiResponse deleteDelegationOfDuty(@PathVariable("id") Long id){
        return new ApiResponse(200, "SUCCESS", delegationOfDutyService.delete(id));
    }

    @PostMapping("/create")
    @ApiOperation(value = "Create a new delegation of duty record. " , response = ApiResponse.class)
    public ApiResponse createDelegationOfDuty(@RequestBody AddDelegationOfDutyDto delegationOfDutyDto){

        DelegationOfDuty delegationOfDuty = modelMapper.map(delegationOfDutyDto, DelegationOfDuty.class);
        delegationOfDuty.setEmployeeByAssignedBy(employeeService.getOne(delegationOfDutyDto.getManagerId()));
       delegationOfDuty.setEmployeeByAssignTo(employeeService.getOne(delegationOfDutyDto.getManagerId()));

       //send delegation of duty email from manager to subordinate on confirmation, could even be over sms
        SimpleMailMessage delegationOfDutyEmail = new SimpleMailMessage();
        delegationOfDutyEmail.setTo(delegationOfDuty.getEmployeeByAssignTo().getEmailAddress());
        delegationOfDutyEmail.setSubject("Assigned Duty Alert");
        delegationOfDutyEmail.setText(" Dear " + delegationOfDuty.getEmployeeByAssignTo().getName().toUpperCase() +" " +delegationOfDuty.getEmployeeByAssignTo().getSurname().toUpperCase()  + ", \n You have been assigned to duty: " +delegationOfDuty.getDuty()
                + "\n Description is as follows: " + delegationOfDuty.getReason() +"\n Duration is from date: " + delegationOfDuty.getFromDate() + " ,To date :"+delegationOfDuty.getToDate()+"\n Please proceed to look into it and if there are any questions or gray areas kindly escalate to the relevant parties.\n Regards\n" + delegationOfDuty.getEmployeeByAssignedBy().getName().toUpperCase() +" "+delegationOfDuty.getEmployeeByAssignedBy().getSurname().toUpperCase());
        delegationOfDutyEmail.setFrom("mchikuruwo@hotmail.com");

        emailService.sendEmail(delegationOfDutyEmail);

        return new ApiResponse(201, "SUCCESS", delegationOfDutyService.add(delegationOfDuty));
    }

    @PutMapping("/edit/{id}")
    @ApiOperation(value = "Edit an existing delegation of duty  record. " +  "Takes dutyID,assigningManagerId and employeeId as path variables",  response = ApiResponse.class)
    public ApiResponse updateDelegationOfDuty(@RequestBody UpdateDelegationOfDutyDto delegationOfDutyDto,
                                              @PathVariable ("id") Long dutyId){

        DelegationOfDuty delegationOfDuty = modelMapper.map(delegationOfDutyDto, DelegationOfDuty.class);
       // delegationOfDuty.setId(delegationOfDutyService.getOne(dutyId));
        delegationOfDuty.setEmployeeByAssignedBy(employeeService.getOne(delegationOfDutyDto.getManagerId()));
        delegationOfDuty.setEmployeeByAssignTo(employeeService.getOne(delegationOfDutyDto.getSubordinateId()));

        // Get details from old record
        DelegationOfDuty oldRecord = delegationOfDutyService.getOne(dutyId);

        delegationOfDuty.setEmployeeByAssignedBy(oldRecord.getEmployeeByAssignedBy());
        delegationOfDuty.setEmployeeByAssignTo(oldRecord.getEmployeeByAssignTo());

        //send update delegation of duty email from manager to subordinate on confirmation, could even be over sms
        SimpleMailMessage updateDelegationOfDutyEmail = new SimpleMailMessage();
        updateDelegationOfDutyEmail.setTo(delegationOfDuty.getEmployeeByAssignTo().getEmailAddress());
        updateDelegationOfDutyEmail.setSubject("Assigned Duty Update Alert");
        updateDelegationOfDutyEmail.setText(" Dear " + delegationOfDuty.getEmployeeByAssignTo().getName().toUpperCase() +" " +delegationOfDuty.getEmployeeByAssignTo().getSurname().toUpperCase()  + ", \n You have been assigned to duty: " +delegationOfDuty.getDuty()
                + "\n Description is as follows: " + delegationOfDuty.getReason() +"\n Duration is from date: " + delegationOfDuty.getFromDate() + " ,To date :"+delegationOfDuty.getToDate()+"\n Please proceed to look into it and if there are any questions or gray areas kindly escalate to the relevant parties.\n Regards\n" + delegationOfDuty.getEmployeeByAssignedBy().getName().toUpperCase() +" "+delegationOfDuty.getEmployeeByAssignTo().getSurname().toUpperCase());
        updateDelegationOfDutyEmail.setFrom("mchikuruwo@hotmail.com");

        emailService.sendEmail(updateDelegationOfDutyEmail);

        return new ApiResponse(200, "SUCCESS", delegationOfDutyService.update(delegationOfDuty));
    }
}
