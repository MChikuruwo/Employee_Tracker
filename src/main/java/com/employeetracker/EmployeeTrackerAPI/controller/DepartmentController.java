package com.employeetracker.EmployeeTrackerAPI.controller;

import com.employeetracker.EmployeeTrackerAPI.api.config.ApiResponse;
import com.employeetracker.EmployeeTrackerAPI.dto.AddDepartmentDto;
import com.employeetracker.EmployeeTrackerAPI.dto.UpdateDepartmentDto;
import com.employeetracker.EmployeeTrackerAPI.models.Department;
import com.employeetracker.EmployeeTrackerAPI.service.iface.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;



@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/departments", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "/api/v1/departments", produces = MediaType.APPLICATION_JSON_VALUE)
public class DepartmentController {

    private final DepartmentService departmentService;
    private final ModelMapper modelMapper;

    @Autowired
    public DepartmentController(DepartmentService departmentService, ModelMapper modelMapper) {
        this.departmentService = departmentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    @ApiOperation(value = "Get all departments", response = ApiResponse.class)
    public ApiResponse getAllDepartments(){
        return new ApiResponse(200, "SUCCESS", departmentService.getAll());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a department by its id. Takes id as a path variable", response = ApiResponse.class)
    public ApiResponse getDepartmentById(@PathVariable("id") Integer id){
        return new ApiResponse(200, "SUCCESS", departmentService.getOne(id));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete a department by its id. Takes id as a path variable", response = ApiResponse.class)
    public ApiResponse deleteDepartment(@PathVariable("id") Integer id){
        return new ApiResponse(200, "SUCCESS", departmentService.delete(id));
    }

    @PostMapping("/by-name")
    @ApiOperation(value = "Get a department its name.", response = ApiResponse.class)
    public ApiResponse getBusinessUnitByName(@RequestBody AddDepartmentDto departmentDto){
        return new ApiResponse(200, "SUCCESS", departmentService.findByDepartment(departmentDto.getDepartment()));
    }

    @PostMapping("/add")
    @ApiOperation(value = "Add a new department", response = ApiResponse.class)
    public ApiResponse addBusinessUnit(@RequestBody AddDepartmentDto departmentDto){
        Department department = modelMapper.map(departmentDto, Department.class);
        return new ApiResponse(201, "SUCCESS", departmentService.add(department));
    }

    @PutMapping("/edit")
    @ApiOperation(value = "Update an existing department", response = ApiResponse.class)
    public ApiResponse updateBusinessUnit(@RequestBody UpdateDepartmentDto departmentDto){
        Department department = modelMapper.map(departmentDto, Department.class);
        return new ApiResponse(200, "SUCCESS", departmentService.update(department));
    }
}
