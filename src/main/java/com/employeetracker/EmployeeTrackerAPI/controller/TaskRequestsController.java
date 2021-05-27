package com.employeetracker.EmployeeTrackerAPI.controller;

import com.employeetracker.EmployeeTrackerAPI.api.config.ApiResponse;
import com.employeetracker.EmployeeTrackerAPI.dto.AddTaskRequestDto;
import com.employeetracker.EmployeeTrackerAPI.dto.UpdateTaskDto;
import com.employeetracker.EmployeeTrackerAPI.dto.UpdateTaskRequestDto;
import com.employeetracker.EmployeeTrackerAPI.enums.TaskRequestStatus;
import com.employeetracker.EmployeeTrackerAPI.enums.TaskStatus;
import com.employeetracker.EmployeeTrackerAPI.models.Employee;
import com.employeetracker.EmployeeTrackerAPI.models.Task;
import com.employeetracker.EmployeeTrackerAPI.models.TaskRequests;
import com.employeetracker.EmployeeTrackerAPI.service.iface.DelegationOfDutyService;
import com.employeetracker.EmployeeTrackerAPI.service.iface.EmailService;
import com.employeetracker.EmployeeTrackerAPI.service.iface.TaskRequestsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

import static java.util.Collections.singleton;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/requests", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "/api/v1/requests", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskRequestsController {

    private final TaskRequestsService taskRequestsService;
    private final DelegationOfDutyService delegationOfDutyService;
    private final EmailService emailService;
    private final ModelMapper modelMapper;

    public TaskRequestsController(TaskRequestsService taskRequestsService, DelegationOfDutyService delegationOfDutyService, EmailService emailService, ModelMapper modelMapper) {
        this.taskRequestsService = taskRequestsService;
        this.delegationOfDutyService = delegationOfDutyService;
        this.emailService = emailService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    @ApiOperation(value = "Get all task requests", response = ApiResponse.class)
    public ApiResponse getAllTaskRequests(){
        return new ApiResponse(200, "SUCCESS", taskRequestsService.getAll());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a task request record by its id. Takes request id as a path variable",  response = ApiResponse.class)
    public ApiResponse getTaskRequestById(@PathVariable("id") Long id){
        return new ApiResponse(200, "SUCCESS", taskRequestsService.getOne(id));
    }

    @GetMapping("/by-assigned-employee/{id}")
    @ApiOperation(value = "Get task requests record by the requesting employee. Takes duty id as a path variable",  response = ApiResponse.class)
    public ApiResponse getTaskByEmployee(@PathVariable("id") Long dutyId){
        Employee employee = delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo();
        return new ApiResponse(200, "SUCCESS", employee);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete a task request. Takes id as a path variable",  response = ApiResponse.class)
    public ApiResponse deleteTask(@PathVariable("id") Long id){
        return new ApiResponse(200, "SUCCESS", taskRequestsService.delete(id));
    }

    @PostMapping("/create/{duty-id}")
    @ApiOperation(value = "Create a new task request record. " + "Takes dutyId as path variable", response = ApiResponse.class)
    public ApiResponse createTask(@RequestBody AddTaskRequestDto taskRequest,
                                  @PathVariable("duty-id") Long dutyId){

        TaskRequests task = modelMapper.map(taskRequest, TaskRequests.class);
        task.setDuty(Collections.singleton(delegationOfDutyService.getOne(dutyId)));
        task.setSubordinate(Collections.singleton(delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo()));



        //send delegation of duty email from manager to subordinate on confirmation, could even be over sms
        SimpleMailMessage taskRequestEmail = new SimpleMailMessage();
        taskRequestEmail.setTo(delegationOfDutyService.getOne(dutyId).getEmployeeByAssignedBy().getEmailAddress());
        taskRequestEmail.setSubject("Task Request Alert");
        taskRequestEmail.setText(" Dear " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignedBy().getName().toUpperCase()+ " " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignedBy().getSurname().toUpperCase() + ",\n Task Request: " + task.getTaskName() +" to duty: " + delegationOfDutyService.getOne(dutyId).getDuty()
                + " assigned to  " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo().getName().toUpperCase() +" " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo().getSurname().toUpperCase()  +" is being requested, with description: "+ task.getDescription()+ "\n Duration from start date: "+task.getStartDate()+","+" to finish date: "+ task.getEndDate()+"."+
                "\nKindly look into it and track progress if there are any questions or gray areas kindly contact "+ delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo().getName().toUpperCase() +" at: " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo().getEmailAddress().toString()+"\n Regards");
        taskRequestEmail.setFrom("mchikuruwo@hotmail.com");

        emailService.sendEmail(taskRequestEmail);

        //set task task status to ACTIVE once it's been processed
        task.setTaskRequestStatus(TaskRequestStatus.PENDING);

        return new ApiResponse(201, "SUCCESS", taskRequestsService.add(task));
    }

    @PutMapping("/edit/{task-id}/{duty-id}")
    @ApiOperation(value = "Edit an existing task request record. " +  "Takes taskId and dutyId as path variables",  response = ApiResponse.class)
    public ApiResponse updateTask(@RequestBody UpdateTaskRequestDto taskDto,
                                  @PathVariable("task-id") Long taskId,
                                  @PathVariable("duty-id") Long dutyId){

        TaskRequests task = modelMapper.map(taskDto, TaskRequests.class);
        taskRequestsService.getOne(taskId);
        task.setDuty(Collections.singleton(delegationOfDutyService.getOne(dutyId)));
        task.setSubordinate(Collections.singleton(delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo()));

        // Get details from old record
        TaskRequests oldRecord = taskRequestsService.getOne(task.getId());

        task.setDuty(oldRecord.getDuty());

        //send delegation of duty email from manager to subordinate on confirmation, could even be over sms
        SimpleMailMessage taskRequestUpdateEmail = new SimpleMailMessage();
        taskRequestUpdateEmail.setTo(delegationOfDutyService.getOne(dutyId).getEmployeeByAssignedBy().getEmailAddress());
        taskRequestUpdateEmail.setSubject("Task Request Update Alert");
        taskRequestUpdateEmail.setText(" Dear " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignedBy().getName().toUpperCase()+ " " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignedBy().getSurname().toUpperCase() + ",\n Task Request: " + task.getTaskName() +" to duty: " + delegationOfDutyService.getOne(dutyId).getDuty()
                + " assigned to  " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo().getName().toUpperCase() +" " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo().getSurname().toUpperCase()  +" is being requested, with description: "+ task.getDescription()+ "\n Duration from start date: "+task.getStartDate()+","+" to finish date: "+ task.getEndDate()+"."+
                "\nKindly look into it and track progress if there are any questions or gray areas kindly contact "+ delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo().getName().toUpperCase() +" at: " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo().getEmailAddress().toString()+"\n Regards");
        taskRequestUpdateEmail.setFrom("mchikuruwo@hotmail.com");

        return new ApiResponse(200, "SUCCESS", taskRequestsService.update(task));
    }
}

