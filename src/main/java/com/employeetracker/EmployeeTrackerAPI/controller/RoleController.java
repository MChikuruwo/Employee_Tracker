package com.employeetracker.EmployeeTrackerAPI.controller;

import com.employeetracker.EmployeeTrackerAPI.api.config.ApiResponse;
import com.employeetracker.EmployeeTrackerAPI.service.iface.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;



@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/roles", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "/api/v1/roles", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleController {

    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @Autowired
    public RoleController(RoleService roleService, ModelMapper modelMapper) {
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    @ApiOperation(value = "Get all roles", response = ApiResponse.class)
    public ApiResponse getAllRoles(){
        return new ApiResponse(200, "SUCCESS", roleService.getAll());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get one role by their ID", response = ApiResponse.class)
    public ApiResponse getOneRoleById(@PathVariable("id") Integer id){
        return new ApiResponse(200, "SUCCESS", roleService.getOne(id));

    }



    @GetMapping("/role-by-name")
    @ApiOperation(value = "Get one role by its name", response = ApiResponse.class)
    public ApiResponse getRoleByName(@RequestParam("name") String name){
        return new ApiResponse(200, "SUCCESS", roleService.findByName(name));
    }
}
