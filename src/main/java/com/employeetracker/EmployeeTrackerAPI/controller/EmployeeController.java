package com.employeetracker.EmployeeTrackerAPI.controller;

import com.employeetracker.EmployeeTrackerAPI.api.config.ApiResponse;
import com.employeetracker.EmployeeTrackerAPI.dto.AddEmployeeDto;
import com.employeetracker.EmployeeTrackerAPI.dto.UpdateEmployeeDto;
import com.employeetracker.EmployeeTrackerAPI.models.Employee;
import com.employeetracker.EmployeeTrackerAPI.models.JobTitle;
import com.employeetracker.EmployeeTrackerAPI.service.iface.EmployeeService;
import com.employeetracker.EmployeeTrackerAPI.service.iface.EmployeeStatusService;
import com.employeetracker.EmployeeTrackerAPI.service.iface.JobTitleService;
import com.employeetracker.EmployeeTrackerAPI.service.iface.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;



@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/employees", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "/api/v1/employees", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeStatusService employeeStatusService;
    private final JobTitleService jobTitleService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeStatusService employeeStatusService, JobTitleService jobTitleService, UserService userService, ModelMapper modelMapper) {
        this.employeeService = employeeService;
        this.employeeStatusService = employeeStatusService;
        this.jobTitleService = jobTitleService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    @ApiOperation(value = "Get all employees", response = ApiResponse.class)
    public ApiResponse getAllEmployees(){
        return new ApiResponse(200, "SUCCESS", employeeService.getAll());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get an employee by their id. Takes id as a path variable", response = ApiResponse.class)
    public ApiResponse getEmployeeById(@PathVariable("id") Integer id){
        return new ApiResponse(200, "SUCCESS", employeeService.getOne(id));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete an employee by their id. Takes id as a path variable", response = ApiResponse.class)
    public ApiResponse deleteEmployee(@PathVariable("id") Integer id){
        return new ApiResponse(200, "SUCCESS", employeeService.delete(id));
    }

    @GetMapping("/by-code")
    @ApiOperation(value = "Get an employee by their employee code. Takes employeeCode as a request parameter",
            response = ApiResponse.class)
    public ApiResponse getEmployeeByEmployeeCode(@RequestParam("employeeCode") String employeeCode){
        return new ApiResponse(200, "SUCCESS", employeeService.findByEmployeeCode(employeeCode));
    }

    @GetMapping("/by-user/{user-id}")
    @ApiOperation(value = "Get an employee by their system user id. Takes userId as a path variable",
            response = ApiResponse.class)
    public ApiResponse getEmployeeByUserId(@PathVariable("user-id") Integer userId){
        return new ApiResponse(200, "SUCCESS", employeeService.findByUserId(userId));
    }

    @GetMapping("/by-status")
    @ApiOperation(value = "Get employees by their employee status. Takes status as a request parameter",
            response = ApiResponse.class)
    public ApiResponse getEmployeesByEmployeeStatus(@RequestParam("status") String status){
        return new ApiResponse(200, "SUCCESS",
                employeeService.findAllByEmployeeStatus(employeeStatusService.findByStatus(status)));
    }

    @GetMapping("/by-job-title/{title-id}")
    @ApiOperation(value = "Get employees by their job title. Takes titleId as a path variable",
            response = ApiResponse.class)
    public ApiResponse getEmployeesByJobTitle(@PathVariable("title-id") Long titleId){
        JobTitle jobTitle = jobTitleService.getOne(titleId);
        return new ApiResponse(200, "SUCCESS", employeeService.findAllByJobTitle(jobTitle));
    }

    @GetMapping("/by-gender")
    @ApiOperation(value = "Get employees by their gender. Takes gender as a request parameter",
            response = ApiResponse.class)
    public ApiResponse getEmployeesByGender(@RequestParam("gender") String gender){
        return new ApiResponse(200, "SUCCESS", employeeService.findAllByGender(gender));
    }

    @PostMapping("/add/{user-id}/{employee-status-id}/{job-title-id}")
    @ApiOperation(value = "Add a new employee. Takes userId, employeeStatusId and jobTitleId as path variables",
            response = ApiResponse.class)
    public ApiResponse addNewEmployee(@RequestBody AddEmployeeDto employeeDto,
                                      @PathVariable("user-id") Integer userId,
                                      @PathVariable("employee-status-id") Long employeeStatusId,
                                      @PathVariable("job-title-id") Long titleId){

        Employee employee = modelMapper.map(employeeDto, Employee.class);
        employee.setUserId(userService.getOne(userId).getId());
        employee.setEmployeeStatus(employeeStatusService.getOne(employeeStatusId));
        employee.setJobTitle(jobTitleService.getOne(titleId));
        //employee.setResidentialStatus(employeeService.);


        return new ApiResponse(200, "SUCCESS", employeeService.add(employee));
    }

    @PutMapping("/edit/{employee-status-id}/{job-title-id}")
    @ApiOperation(value = "Update an existing employee. Takes employeeStatusId and jobTitleId as path variables",
            response = ApiResponse.class)
    public ApiResponse updateAnExistingEmployee(@RequestBody UpdateEmployeeDto employeeDto,
                                                @PathVariable("employee-status-id") Long statusId,
                                                @PathVariable("job-title-id") Long titleId){

        Employee employee = modelMapper.map(employeeDto, Employee.class);
        employee.setJobTitle(jobTitleService.getOne(titleId));
        employee.setEmployeeStatus(employeeStatusService.getOne(statusId));

        // Get old record to get the userId
        Employee oldRecord = employeeService.getOne(employeeDto.getId());

        employee.setUserId(oldRecord.getUserId());
        return new ApiResponse(200, "SUCCESS", employeeService.update(employee));
    }
}
