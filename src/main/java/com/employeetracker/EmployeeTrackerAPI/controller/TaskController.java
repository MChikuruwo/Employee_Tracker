package com.employeetracker.EmployeeTrackerAPI.controller;

import com.employeetracker.EmployeeTrackerAPI.api.config.ApiResponse;
import com.employeetracker.EmployeeTrackerAPI.dto.AddDelegationOfDutyDto;
import com.employeetracker.EmployeeTrackerAPI.dto.AddTaskDto;
import com.employeetracker.EmployeeTrackerAPI.dto.UpdateDelegationOfDutyDto;
import com.employeetracker.EmployeeTrackerAPI.dto.UpdateTaskDto;
import com.employeetracker.EmployeeTrackerAPI.enums.TaskStatus;
import com.employeetracker.EmployeeTrackerAPI.models.DelegationOfDuty;
import com.employeetracker.EmployeeTrackerAPI.models.Employee;
import com.employeetracker.EmployeeTrackerAPI.models.Task;
import com.employeetracker.EmployeeTrackerAPI.service.iface.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;

import static java.util.Collections.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "/api/v1/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController {

    private final TaskService taskService;
    private final TaskImportanceService taskImportanceService;
    private final EmailService emailService;
    private final EmployeeService employeeService;
    private final DelegationOfDutyService delegationOfDutyService;
    private  final ModelMapper modelMapper;

    @Autowired
    public TaskController(TaskService taskService, TaskImportanceService taskImportanceService ,EmailService emailService, EmployeeService employeeService, DelegationOfDutyService delegationOfDutyService, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.taskImportanceService = taskImportanceService;
        this.emailService = emailService;
        this.employeeService = employeeService;
        this.delegationOfDutyService = delegationOfDutyService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    @ApiOperation(value = "Get all tasks", response = ApiResponse.class)
    public ApiResponse getAllTasks(){
        return new ApiResponse(200, "SUCCESS", taskService.getAll());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a task record by its id. Takes id as a path variable",  response = ApiResponse.class)
    public ApiResponse getTaskById(@PathVariable("id") Long id){
        return new ApiResponse(200, "SUCCESS", taskService.getOne(id));
    }

    @GetMapping("/by-assigned-employee/{id}")
    @ApiOperation(value = "Get tasks record by the assigned employee. Takes id as a path variable",  response = ApiResponse.class)
    public ApiResponse getTaskByEmployee(@PathVariable("id") Integer employeeId){
        Employee employee = employeeService.getOne(employeeId);
        return new ApiResponse(200, "SUCCESS", taskService.findAllByAssignedTo(employee));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete a task. Takes id as a path variable",  response = ApiResponse.class)
    public ApiResponse deleteTask(@PathVariable("id") Long id){
        return new ApiResponse(200, "SUCCESS", taskService.delete(id));
    }

    @PostMapping("/create/{duty-id}/{employee-id}/{task-importance-id}")
    @ApiOperation(value = "Create a new delegation of duty record. " + "Takes managerId and employeeId as path variables", response = ApiResponse.class)
    public ApiResponse createTask(@RequestBody AddTaskDto taskDto,
                                              @PathVariable("duty-id") Long dutyId,
                                              @PathVariable("employee-id") Integer employeeId,
                                              @PathVariable("task-importance-id") Long taskImportanceId){

        Task task = modelMapper.map(taskDto, Task.class);
        task.setDelegatedDuty(delegationOfDutyService.getOne(dutyId));
        task.setAssignedTo(singleton(employeeService.getOne(employeeId)));
        task.setImportance(taskImportanceService.getOne(taskImportanceId));
        //get start and finish dates from the duty duration dates
       task.setStartDate(delegationOfDutyService.getOne(dutyId).getFromDate());
        task.setDueDate(delegationOfDutyService.getOne(dutyId).getToDate());


        //send delegation of duty email from manager to subordinate on confirmation, could even be over sms
        SimpleMailMessage taskProcessingEmail = new SimpleMailMessage();
        taskProcessingEmail.setTo(delegationOfDutyService.getOne(dutyId).getEmployeeByAssignedBy().getEmailAddress());
        taskProcessingEmail.setSubject("Task Creation Alert");
        taskProcessingEmail.setText(" Dear " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignedBy().getName().toUpperCase()+ " " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignedBy().getSurname().toUpperCase() + ",\n Task: " + task.getName() +" to duty: " + delegationOfDutyService.getOne(dutyId).getDuty()
                + " assigned to  " + employeeService.getOne(employeeId).getName()+" " + employeeService.getOne(employeeId).getSurname()  +" has been successfully created, with description: "+ task.getDescription()+ "\n Duration from start date: "+task.getActualStartDate()+","+" to finish date: "+ task.getActualEndDate()+"."+
                "\nKindly look into it and track progress if there are any questions or gray areas kindly contact "+ delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo().getName().toUpperCase() +" at: " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo().getEmailAddress().toString()+"\n Regards");
        taskProcessingEmail.setFrom("mchikuruwo@hotmail.com");

        emailService.sendEmail(taskProcessingEmail);

        //set task task status to ACTIVE once it's been processed
        task.setTaskStatus(TaskStatus.ACTIVE);

        return new ApiResponse(201, "SUCCESS", taskService.add(task));
    }

    @PutMapping("/edit/{duty-id}/{employee-id}/{task-importance-id}")
    @ApiOperation(value = "Edit an existing delegation of duty  record. " +  "Takes assigningManagerId and employeeId as path variables",  response = ApiResponse.class)
    public ApiResponse updateTask(@RequestBody UpdateTaskDto taskDto,
                                  @PathVariable("duty-id") Long dutyId,
                                  @PathVariable("employee-id") Integer employeeId,
                                  @PathVariable("task-importance-id") Long taskImportanceId){

        Task task = modelMapper.map(taskDto, Task.class);
        task.setDelegatedDuty(delegationOfDutyService.getOne(dutyId));
        task.setAssignedTo(singleton(employeeService.getOne(employeeId)));
        task.setImportance(taskImportanceService.getOne(taskImportanceId));

        // Get details from old record
        Task oldRecord = taskService.getOne(task.getId());

        task.setDelegatedDuty(oldRecord.getDelegatedDuty());
        task.setAssignedTo(oldRecord.getAssignedTo());
        task.setImportance(oldRecord.getImportance());

        //send delegation of duty email from manager to subordinate on confirmation, could even be over sms
        SimpleMailMessage taskProcessingEmail = new SimpleMailMessage();
        taskProcessingEmail.setTo(delegationOfDutyService.getOne(dutyId).getEmployeeByAssignedBy().getEmailAddress());
        taskProcessingEmail.setSubject("Task Update Alert");
        taskProcessingEmail.setText(" Dear " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignedBy().getName().toUpperCase()+ " " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignedBy().getSurname().toUpperCase() + ",\n Task: " + task.getName() +" to duty: " + delegationOfDutyService.getOne(dutyId).getDuty()
                + " assigned to  " + employeeService.getOne(employeeId).getName()+" " + employeeService.getOne(employeeId).getSurname()  +" has been successfully updated, with description: "+ task.getDescription()+ "\n Duration from start date: "+task.getActualStartDate()+","+" to finish date: "+ task.getActualEndDate()+"."+
                "\nKindly look into it and track progress if there are any questions or gray areas kindly contact "+ delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo().getName().toUpperCase() +" at: " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo().getEmailAddress().toString()+"\n Regards");
        taskProcessingEmail.setFrom("mchikuruwo@hotmail.com");

        return new ApiResponse(200, "SUCCESS", taskService.update(task));
    }
}
