package com.employeetracker.EmployeeTrackerAPI.controller;

import com.employeetracker.EmployeeTrackerAPI.api.config.ApiResponse;
import com.employeetracker.EmployeeTrackerAPI.dto.*;
import com.employeetracker.EmployeeTrackerAPI.models.JobTitle;
import com.employeetracker.EmployeeTrackerAPI.models.Location;
import com.employeetracker.EmployeeTrackerAPI.models.User;
import com.employeetracker.EmployeeTrackerAPI.service.iface.LocationService;
import com.employeetracker.EmployeeTrackerAPI.service.iface.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@RestController
@RequestMapping(value = "/api/v1/location", produces = MediaType.APPLICATION_JSON_VALUE)
//@CrossOrigin(origins = "http://localhost:5109")
@Api(value = "/api/v1/location", produces = MediaType.APPLICATION_JSON_VALUE)
public class LocationController {
    private final LocationService locationService;
    private final LoginService loginService;
    private final ModelMapper modelMapper;

    @Autowired
    public LocationController(LocationService locationService, LoginService loginService, ModelMapper modelMapper) {
        this.locationService = locationService;
        this.loginService = loginService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    @ApiOperation(value = "Get all locations", response = ApiResponse.class)
    public ApiResponse getAllLocations(){
        return new ApiResponse(200, "SUCCESS", locationService.getAll());
    }

    @GetMapping("/one/{id}")
    @ApiOperation(value = "Get a location by taking its ID", response = ApiResponse.class)
    public ApiResponse getOneLocation(@PathVariable("id") Long id) {
        return new ApiResponse(200, "SUCCESS", locationService.getOne(id));
    }

    @PostMapping("/add/{login-id}")
    @ApiOperation(value = "Add a new location. Takes LoginId as path variable",
            response = ApiResponse.class)
    public ApiResponse addLocation(@RequestBody AddLocationDto addLocationDto,
                                   @PathVariable("login-id") Integer loginId){

        //map the location class
        Location location = modelMapper.map(addLocationDto,Location.class);

        //get Login id
        location.setLogins(Collections.singleton(loginService.getOne(loginId)));

        return new ApiResponse(200, "SUCCESS", locationService.add(location));
    }

    @PutMapping("/edit")
    @ApiOperation(value = "Update location of an existing login", response = ApiResponse.class)
    public ApiResponse updateLocation(@RequestBody UpdateLocationDto updateLocationDto){
        Location location = modelMapper.map(updateLocationDto, Location.class);

        // Get business unit and department from old database record
        Location oldLocation = locationService.getOne(updateLocationDto.getId());

        return new ApiResponse(200, "SUCCESS", locationService.update(location));
    }

    @GetMapping("/by-longitude/{longitude}")
    @ApiOperation(value = "Get a location by its longitude. Takes longitude as a request parameter",  response = ApiResponse.class)
    public ApiResponse getLocationByLongitude(@RequestParam("longitude") String longitude){
        return new ApiResponse(200, "SUCCESS", locationService.findByLongitude(longitude));
    }

    @GetMapping("/by-latitude/{latitude}")
    @ApiOperation(value = "Get a location by its latitude. Takes latitude as a request parameter",  response = ApiResponse.class)
    public ApiResponse getLocationByLatitude(@RequestParam("latitude") String latitude){
        return new ApiResponse(200, "SUCCESS", locationService.findByLongitude(latitude));
    }
}
