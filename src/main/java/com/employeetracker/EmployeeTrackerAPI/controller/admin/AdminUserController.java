package com.employeetracker.EmployeeTrackerAPI.controller.admin;

import com.employeetracker.EmployeeTrackerAPI.api.config.ApiResponse;
import com.employeetracker.EmployeeTrackerAPI.dto.UpdateUserDto;
import com.employeetracker.EmployeeTrackerAPI.models.User;
import com.employeetracker.EmployeeTrackerAPI.service.iface.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;



@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/admin/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "/api/v1/admin", produces = MediaType.APPLICATION_JSON_VALUE)
//@RolesAllowed("ADMIN")
public class AdminUserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminUserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;

    }

    @GetMapping("/")
    @ApiOperation(value = "Get all users", response = ApiResponse.class)
    public ApiResponse getAllUsers(){
        return new ApiResponse(200, "SUCCESS", userService.getAll());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get one user by their ID", response = ApiResponse.class)
    public ApiResponse getOneUser(@PathVariable("id") Integer id) {
        return new ApiResponse(200, "SUCCESS", userService.getOne(id));
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Delete user by their ID", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse deleteUser(@PathVariable("id") Integer id){
        return new ApiResponse(200, "SUCCESS", userService.delete(id));
    }


    @PutMapping(value = "/users/edit/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Updates a current user's details", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse updateUser(@RequestBody UpdateUserDto updateUserDto, @PathVariable("id") Integer id){
        User user = modelMapper.map(updateUserDto, User.class);
        userService.getOne(id);
        return new ApiResponse(200, "SUCCESS", userService.update(user));
    }



}
