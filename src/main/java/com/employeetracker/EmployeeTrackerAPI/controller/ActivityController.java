package com.employeetracker.EmployeeTrackerAPI.controller;

import com.employeetracker.EmployeeTrackerAPI.api.config.ApiResponse;
import com.employeetracker.EmployeeTrackerAPI.dto.ActivityDto;
import com.employeetracker.EmployeeTrackerAPI.models.Activity;
import com.employeetracker.EmployeeTrackerAPI.service.iface.ActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/activity", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "/api/v1/activity", produces = MediaType.APPLICATION_JSON_VALUE)
public class ActivityController {
    private final ActivityService activityService;
    private final ModelMapper modelMapper;

    @Autowired
    public ActivityController(ActivityService activityService, ModelMapper modelMapper) {
        this.activityService = activityService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(value = "/log", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "logging user activity ", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse addCategory(@RequestBody ActivityDto activityDto) {
        Activity activity = modelMapper.map(activityDto, Activity.class);
        return new ApiResponse(200, "SUCCESS", activityService.add(activity));
    }

    @GetMapping("/")
    @ApiOperation(value = "Get all activities", response = ApiResponse.class)
    public ApiResponse getAllActivities() {
        return new ApiResponse(200, "SUCCESS", activityService.getAll());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get single activity by its ID", response = ApiResponse.class)
    public ApiResponse getById(@PathVariable("id") Long id) {
        return new ApiResponse(200, "SUCCESS", activityService.getById(id));
    }

    @GetMapping("/{entityId}")
    @ApiOperation(value = "Get all activities performed on a particular entity", response = ApiResponse.class)
    public ApiResponse getAllActivitiesPerformedOnEntity(@PathVariable("entityId") Long entityId) {
        return new ApiResponse(200, "SUCCESS", activityService.getAllByEntityId(entityId));
    }

}
