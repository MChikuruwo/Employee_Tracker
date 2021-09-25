package com.employeetracker.EmployeeTrackerAPI.controller;

import com.employeetracker.EmployeeTrackerAPI.api.config.ApiResponse;
import com.employeetracker.EmployeeTrackerAPI.dto.AddJobTitleDto;
import com.employeetracker.EmployeeTrackerAPI.dto.UpdateJobTitleDto;
import com.employeetracker.EmployeeTrackerAPI.models.BusinessUnit;
import com.employeetracker.EmployeeTrackerAPI.models.Department;
import com.employeetracker.EmployeeTrackerAPI.models.JobTitle;
import com.employeetracker.EmployeeTrackerAPI.service.iface.BusinessUnitService;
import com.employeetracker.EmployeeTrackerAPI.service.iface.DepartmentService;
import com.employeetracker.EmployeeTrackerAPI.service.iface.JobTitleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/job-titles", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "/api/v1/job-titles", produces = MediaType.APPLICATION_JSON_VALUE)
public class JobTitleController {

    private final JobTitleService jobTitleService;
    private final BusinessUnitService businessUnitService;
    private final DepartmentService departmentService;
    private final ModelMapper modelMapper;

    @Autowired
    public JobTitleController(JobTitleService jobTitleService, BusinessUnitService businessUnitService, DepartmentService departmentService, ModelMapper modelMapper) {
        this.jobTitleService = jobTitleService;
        this.businessUnitService = businessUnitService;
        this.departmentService = departmentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    @ApiOperation(value = "Get all job titles", response = ApiResponse.class)
    public ApiResponse getAllJobTitles(){
        return new ApiResponse(200, "SUCCESS", jobTitleService.getAll());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a job title by its id. Takes id as a path variable", response = ApiResponse.class)
    public ApiResponse getJobTitleById(@PathVariable("id") Long id){
        return new ApiResponse(200, "SUCCESS", jobTitleService.getOne(id));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete a job title by its id. Takes id as a path variable", response = ApiResponse.class)
    public ApiResponse deleteJobTitle(@PathVariable("id") Long id){
        return new ApiResponse(200, "SUCCESS", jobTitleService.delete(id));
    }

    @PostMapping("/add")
    @ApiOperation(value = "Add a new job title.",
            response = ApiResponse.class)
    public ApiResponse addJobTitle(@RequestBody AddJobTitleDto jobTitleDto){

        JobTitle jobTitle = modelMapper.map(jobTitleDto, JobTitle.class);
        jobTitle.setBusinessUnit(businessUnitService.getOne(jobTitleDto.getBusinessUnitId()));
        jobTitle.setDepartment(departmentService.getOne(jobTitleDto.getDepartmentId()));

        return new ApiResponse(200, "SUCCESS", jobTitleService.add(jobTitle));
    }

    @PutMapping("/edit")
    @ApiOperation(value = "Edit an existing job title", response = ApiResponse.class)
    public ApiResponse updateJobTitle(@RequestBody UpdateJobTitleDto updateJobTitleDto){
        JobTitle jobTitle = modelMapper.map(updateJobTitleDto, JobTitle.class);

        // Get business unit and department from old database record
        JobTitle oldJobTitle = jobTitleService.getOne(updateJobTitleDto.getId());

        jobTitle.setDepartment(oldJobTitle.getDepartment());
        jobTitle.setBusinessUnit(oldJobTitle.getBusinessUnit());

        return new ApiResponse(200, "SUCCESS", jobTitleService.update(jobTitle));
    }

    @GetMapping("/by-business-unit/{unit-id}")
    @ApiOperation(value = "Get job titles under a business unit. Takes unitId as a path variable", response = ApiResponse.class)
    public ApiResponse getJobTitlesByBusinessUnit(@PathVariable("unit-id") Integer id){
        BusinessUnit businessUnit = businessUnitService.getOne(id);
        return new ApiResponse(200, "SUCCESS", jobTitleService.findAllByBusinessUnit(businessUnit));
    }

    @GetMapping("/by-department/{department-id}")
    @ApiOperation(value = "Get job titles under a department. Takes departmentId as a path variable", response = ApiResponse.class)
    public ApiResponse getJobTitlesByDepartment(@PathVariable("department-id") Integer id){
        Department department = departmentService.getOne(id);
        return new ApiResponse(200, "SUCCESS", jobTitleService.findAllByDepartment(department));
    }

    @GetMapping("/by-unit-and-department/{unit-id}/{department-id}")
    @ApiOperation(value = "Get job titles under a business unit and department. Takes unitId and departmentId as path variables", response = ApiResponse.class)
    public ApiResponse getJobTitlesByBusinessUnitAndDepartment(@PathVariable("unit-id") Integer unitId,
                                                                    @PathVariable("department-id") Integer departmentId){
        BusinessUnit businessUnit = businessUnitService.getOne(unitId);
        Department department = departmentService.getOne(departmentId);

        return new ApiResponse(200, "SUCCESS",
                jobTitleService.findAllByBusinessUnitAndDepartment(businessUnit, department));

    }


}
