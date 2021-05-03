package com.employeetracker.EmployeeTrackerAPI.controller;


import com.employeetracker.EmployeeTrackerAPI.api.config.ApiResponse;
import com.employeetracker.EmployeeTrackerAPI.dto.AddDelegationOfDutyDto;
import com.employeetracker.EmployeeTrackerAPI.dto.UpdateDelegationOfDutyDto;
import com.employeetracker.EmployeeTrackerAPI.models.DelegationOfDuty;
import com.employeetracker.EmployeeTrackerAPI.models.Employee;
import com.employeetracker.EmployeeTrackerAPI.service.iface.DelegationOfDutyService;
import com.employeetracker.EmployeeTrackerAPI.service.iface.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/delegation-of-duties", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "/api/v1/delegation-of-duties", produces = MediaType.APPLICATION_JSON_VALUE)

public class DelegationOfDutyController {

    private final DelegationOfDutyService delegationOfDutyService;
    private final EmployeeService employeeService;
    private final ModelMapper modelMapper;

    @Autowired
    public DelegationOfDutyController(DelegationOfDutyService delegationOfDutyService, EmployeeService employeeService,ModelMapper modelMapper) {
        this.delegationOfDutyService = delegationOfDutyService;
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
    }
    @GetMapping("/")
    @ApiOperation(value = "Get all delegation of  duty requests", response = ApiResponse.class)
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
    public ApiResponse getDelegationOfDutyByAssigningManager(@PathVariable("id") Long managerId){
        Employee manager = employeeService.getOne(managerId);
        return new ApiResponse(200, "SUCCESS",
                delegationOfDutyService.findAllByAssigningManager(manager));
    }

    @GetMapping("/by-assigned-employee/{id}")
    @ApiOperation(value = "Get a delegation of duty record by the transferring employee. Takes id as a path variable",
            response = ApiResponse.class)
    public ApiResponse getDelegationOfDutyByEmployee(@PathVariable("id") Long employeeId){
        Employee employee = employeeService.getOne(employeeId);
        return new ApiResponse(200, "SUCCESS",
                delegationOfDutyService.findAllByAssignedEmployee(employee));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete a delegation of duty  record. Takes id as a path variable",
            response = ApiResponse.class)
    public ApiResponse deleteDelegationOfDuty(@PathVariable("id") Long id){
        return new ApiResponse(200, "SUCCESS", delegationOfDutyService.delete(id));
    }

    @PostMapping("/create/{manager-id}/{employee-id}")
    @ApiOperation(value = "Create a new delegation of duty record. " +
            "Takes managerId and employeeId as path variables",
            response = ApiResponse.class)
    public ApiResponse createStaffTransfer(@RequestBody AddDelegationOfDutyDto delegationOfDutyDto,
                                           @PathVariable("manager-id") Long managerId,
                                           @PathVariable("employee-id") Long employeeId){

        DelegationOfDuty delegationOfDuty = modelMapper.map(delegationOfDutyDto, DelegationOfDuty.class);
        delegationOfDuty.setEmployeeByAssignedBy(employeeService.getOne(managerId));
       delegationOfDuty.setEmployeeByAssignTo(employeeService.getOne(employeeId));

        return new ApiResponse(201, "SUCCESS", delegationOfDutyService.add(delegationOfDuty));
    }

    @PutMapping("/edit/{assigning-manager-id}/{assigned-employee-id}")
    @ApiOperation(value = "Edit an existing delegation of duty  record. " +
            "Takes assigningManagerId and employeeId as path variables",
            response = ApiResponse.class)
    public ApiResponse updateDelegationOfDuty(@RequestBody UpdateDelegationOfDutyDto delegationOfDutyDto,
                                           @PathVariable("assigning-manager-id") Long managerId,
                                           @PathVariable("assigned-employee-id") Long employeeId){

        DelegationOfDuty delegationOfDuty = modelMapper.map(delegationOfDutyDto, DelegationOfDuty.class);
        delegationOfDuty.setEmployeeByAssignedBy(employeeService.getOne(managerId));
        delegationOfDuty.setEmployeeByAssignTo(employeeService.getOne(employeeId));

        // Get details from old record
        DelegationOfDuty oldRecord = delegationOfDutyService.getOne(delegationOfDuty.getId());

        delegationOfDuty.setEmployeeByAssignedBy(oldRecord.getEmployeeByAssignedBy());
        delegationOfDuty.setEmployeeByAssignTo(oldRecord.getEmployeeByAssignTo());
        return new ApiResponse(200, "SUCCESS", delegationOfDutyService.update(delegationOfDuty));
    }
}
