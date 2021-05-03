package com.employeetracker.EmployeeTrackerAPI.controller;

import com.employeetracker.EmployeeTrackerAPI.api.config.ApiResponse;
import com.employeetracker.EmployeeTrackerAPI.dto.AddNoticeDto;
import com.employeetracker.EmployeeTrackerAPI.dto.UpdateNoticesDto;
import com.employeetracker.EmployeeTrackerAPI.models.Notices;
import com.employeetracker.EmployeeTrackerAPI.service.iface.NoticesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/notices", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "/api/v1/notices", produces = MediaType.APPLICATION_JSON_VALUE)
public class NoticesController {

    private final NoticesService noticesService;
    private final ModelMapper modelMapper;

    @Autowired
    public NoticesController(NoticesService noticesService, ModelMapper modelMapper) {
        this.noticesService = noticesService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    @ApiOperation(value = "Get all notices", response = ApiResponse.class)
    public ApiResponse getAllNotices(){
        return new ApiResponse(200, "SUCCESS", noticesService.getAll());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get one notice by its ID", response = ApiResponse.class)
    public ApiResponse getOneNoticeById(@PathVariable("id") Long id){
        return new ApiResponse(200, "SUCCESS", noticesService.getOne(id));

    }
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete a notice by its id. Takes id as a path variable", response = ApiResponse.class)
    public ApiResponse deleteNotice(@PathVariable("id") Long id){
        return new ApiResponse(200, "SUCCESS", noticesService.delete(id));
    }


    @PostMapping("/add")
    @ApiOperation(value = "Add a new notice to notice board", response = ApiResponse.class)
    public ApiResponse addNotice(@RequestBody AddNoticeDto noticeDto) {

        Notices notice = modelMapper.map(noticeDto, Notices.class);

        return new ApiResponse(200, "SUCCESS", noticesService.add(notice));

    }
    @PutMapping("/edit")
    @ApiOperation(value = "Update an existing Notice", response = ApiResponse.class)
    public ApiResponse updateNotice(@RequestBody UpdateNoticesDto noticesDto){
        Notices notice = modelMapper.map(noticesDto, Notices.class);
        return new ApiResponse(200, "SUCCESS", noticesService.update(notice));
    }

    @GetMapping("/notice-by-title")
    @ApiOperation(value = "Get one notice by its title", response = ApiResponse.class)
    public ApiResponse getNoticeByTitle(@RequestParam("title") String title){
        return new ApiResponse(200, "SUCCESS", noticesService.findByTitle(title));
    }
}

