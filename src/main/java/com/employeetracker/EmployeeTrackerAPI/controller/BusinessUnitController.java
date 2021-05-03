package com.employeetracker.EmployeeTrackerAPI.controller;

import com.employeetracker.EmployeeTrackerAPI.api.config.ApiResponse;
import com.employeetracker.EmployeeTrackerAPI.dto.AddBusinessUnitDto;
import com.employeetracker.EmployeeTrackerAPI.dto.UpdateBusinessUnitDto;
import com.employeetracker.EmployeeTrackerAPI.models.BusinessUnit;
import com.employeetracker.EmployeeTrackerAPI.service.iface.BusinessUnitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/business-units", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "/api/v1/business-units", produces = MediaType.APPLICATION_JSON_VALUE)
public class BusinessUnitController {

    private final BusinessUnitService businessUnitService;
    private final ModelMapper modelMapper;

    @Autowired
    public BusinessUnitController(BusinessUnitService businessUnitService, ModelMapper modelMapper) {
        this.businessUnitService = businessUnitService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    @ApiOperation(value = "Get all business units", response = ApiResponse.class)
    public ApiResponse getAllBusinessUnits(){
        return new ApiResponse(200, "SUCCESS", businessUnitService.getAll());
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "Get a business unit by its id. Takes id as a path variable", response = ApiResponse.class)
    public ApiResponse getBusinessUnitById(@PathVariable("id") Integer id){
        return new ApiResponse(200, "SUCCESS", businessUnitService.getOne(id));
    }
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete a business unit by its id. Takes id as a path variable", response = ApiResponse.class)
    public ApiResponse deleteBusinessUnit(@PathVariable("id") Integer id){
        return new ApiResponse(200, "SUCCESS", businessUnitService.delete(id));
    }

    @PostMapping("/by-name")
    @ApiOperation(value = "Get a business unit by its name.", response = ApiResponse.class)
    public ApiResponse getBusinessUnitByName(@RequestBody AddBusinessUnitDto businessUnit){
        return new ApiResponse(200, "SUCCESS", businessUnitService.findByBusinessUnit(businessUnit.getBusinessUnit()));
    }

    @PostMapping("/add")
    @ApiOperation(value = "Add a new business unit", response = ApiResponse.class)
    public ApiResponse addBusinessUnit(@RequestBody AddBusinessUnitDto businessUnitDto){
        BusinessUnit businessUnit = modelMapper.map(businessUnitDto, BusinessUnit.class);
        return new ApiResponse(201, "SUCCESS", businessUnitService.add(businessUnit));
    }

    @PutMapping("/edit")
    @ApiOperation(value = "Update an existing business unit", response = ApiResponse.class)
    public ApiResponse updateBusinessUnit(@RequestBody UpdateBusinessUnitDto businessUnitDto){
        BusinessUnit businessUnit = modelMapper.map(businessUnitDto, BusinessUnit.class);
        return new ApiResponse(200, "SUCCESS", businessUnitService.update(businessUnit));
    }
}
