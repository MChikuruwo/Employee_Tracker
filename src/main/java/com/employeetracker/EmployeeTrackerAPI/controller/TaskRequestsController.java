package com.employeetracker.EmployeeTrackerAPI.controller;

import com.employeetracker.EmployeeTrackerAPI.api.config.ApiResponse;
import com.employeetracker.EmployeeTrackerAPI.dto.AddTaskRequestDto;
import com.employeetracker.EmployeeTrackerAPI.dto.UpdateTaskDto;
import com.employeetracker.EmployeeTrackerAPI.dto.UpdateTaskRequestDto;
import com.employeetracker.EmployeeTrackerAPI.enums.TaskProgress;
import com.employeetracker.EmployeeTrackerAPI.enums.TaskRequestAction;
import com.employeetracker.EmployeeTrackerAPI.enums.TaskRequestStatus;
import com.employeetracker.EmployeeTrackerAPI.enums.TaskStatus;
import com.employeetracker.EmployeeTrackerAPI.models.Employee;
import com.employeetracker.EmployeeTrackerAPI.models.Task;
import com.employeetracker.EmployeeTrackerAPI.models.TaskRequests;
import com.employeetracker.EmployeeTrackerAPI.service.iface.DelegationOfDutyService;
import com.employeetracker.EmployeeTrackerAPI.service.iface.EmailService;
import com.employeetracker.EmployeeTrackerAPI.service.iface.EmployeeService;
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
    private final EmployeeService employeeService;
    private final ModelMapper modelMapper;

    public TaskRequestsController(TaskRequestsService taskRequestsService, DelegationOfDutyService delegationOfDutyService, EmailService emailService,EmployeeService employeeService, ModelMapper modelMapper) {
        this.taskRequestsService = taskRequestsService;
        this.delegationOfDutyService = delegationOfDutyService;
        this.emailService = emailService;
        this.employeeService = employeeService;
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

    @PostMapping("/create")
    @ApiOperation(value = "Create a new task request record." , response = ApiResponse.class)
    public ApiResponse createTask(@RequestBody AddTaskRequestDto taskRequest){

        TaskRequests task = modelMapper.map(taskRequest, TaskRequests.class);
        task.setDelegatedDuty((delegationOfDutyService.getOne(taskRequest.getDutyId())));
        task.setSubordinate(Collections.singleton(employeeService.getOne(taskRequest.getEmployeeId())));



        //send delegation of duty email from manager to subordinate on confirmation, could even be over sms
        SimpleMailMessage taskRequestEmail = new SimpleMailMessage();
        taskRequestEmail.setTo((delegationOfDutyService.getOne(taskRequest.getDutyId())).getEmployeeByAssignedBy().getEmailAddress());
        taskRequestEmail.setSubject("Task Request Alert");
        taskRequestEmail.setText(" Dear " + (delegationOfDutyService.getOne(taskRequest.getDutyId())).getEmployeeByAssignedBy().getName().toUpperCase()+ " " + (delegationOfDutyService.getOne(taskRequest.getDutyId())).getEmployeeByAssignedBy().getSurname().toUpperCase() + ",\n Task Request: " + task.getTaskName() +" to duty: " + (delegationOfDutyService.getOne(taskRequest.getDutyId())).getDuty()
                + " assigned to  " + (delegationOfDutyService.getOne(taskRequest.getDutyId())).getEmployeeByAssignTo().getName().toUpperCase() +" " + (delegationOfDutyService.getOne(taskRequest.getDutyId())).getEmployeeByAssignTo().getSurname().toUpperCase()  +" is being requested, with description: "+ task.getDescription()+ "\n Duration from start date: "+task.getStartDate()+","+" to finish date: "+ task.getEndDate()+"."+
                "\nKindly look into it and track progress if there are any questions or gray areas kindly contact "+ (delegationOfDutyService.getOne(taskRequest.getDutyId())).getEmployeeByAssignTo().getName().toUpperCase() +" at: " + (delegationOfDutyService.getOne(taskRequest.getDutyId())).getEmployeeByAssignTo().getEmailAddress().toString()+"\n Regards");
        taskRequestEmail.setFrom("mchikuruwo@hotmail.com");

        emailService.sendEmail(taskRequestEmail);

        //set task task status to ACTIVE once it's been processed
        task.setTaskRequestStatus(TaskRequestStatus.PENDING); //DEFAULT
        task.setTaskRequestAction(TaskRequestAction.ADD); //DEFAULT
        task.setTaskStatus(TaskStatus.PENDING); //DEFAULT
        task.setTaskProgress(TaskProgress.INITIAL); //DEFAULT

        return new ApiResponse(201, "SUCCESS", taskRequestsService.add(task));
    }

    @PutMapping("/edit/{task-id}/{duty-id}")
    @ApiOperation(value = "Edit an existing task request record. " +  "Takes taskId and dutyId as path variables",  response = ApiResponse.class)
    public ApiResponse updateTask(@RequestBody UpdateTaskRequestDto taskDto,
                                  @PathVariable("task-id") Long taskId,
                                  @PathVariable("duty-id") Long dutyId){

        TaskRequests task = modelMapper.map(taskDto, TaskRequests.class);
        taskRequestsService.getOne(taskId);
        task.setDelegatedDuty(delegationOfDutyService.getOne(taskDto.getDutyId()));
        task.setSubordinate(Collections.singleton(delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo()));

        // Get details from old record
        TaskRequests oldRecord = taskRequestsService.getOne(task.getId());

        task.setDelegatedDuty(oldRecord.getDelegatedDuty());

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
    
    @PutMapping("/validate/{task-id}/{duty-id}")
    @ApiOperation(value="Approval of a task request. " + "Takes taskRequestId and dutyId as path variables", response = ApiResponse.class)
    public ApiResponse approveTaskRequest( @PathVariable("task-id") Long taskId,
                                           @PathVariable("duty-id") Long dutyId){

        TaskRequests task = taskRequestsService.getOne(taskId);
        task.setDelegatedDuty((delegationOfDutyService.getOne(dutyId)));

        //send task request validation email, could even be over sms
        SimpleMailMessage taskValidationEmail = new SimpleMailMessage();
        taskValidationEmail.setTo(delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo().getEmailAddress());
        taskValidationEmail.setSubject("Task Request Approval Alert");
        taskValidationEmail.setText(" Dear " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo().getName().toUpperCase()+ " " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo().getSurname().toUpperCase() + ",\n Task Request: " + task.getTaskName() +" to duty: " + delegationOfDutyService.getOne(dutyId).getDuty()
                + " requested to  " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignedBy().getName().toUpperCase() +" " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignedBy().getSurname().toUpperCase()  +" , with description: "+ task.getDescription()+ "\n Duration from start date: "+task.getStartDate()+","+" to finish date: "+ task.getEndDate()+"."+ " has been " + task.getTaskRequestStatus() +
                "\nKindly look into it and if there is any more information on the approved task kindly contact "+ delegationOfDutyService.getOne(dutyId).getEmployeeByAssignedBy().getName().toUpperCase() +" at: " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignedBy().getEmailAddress().toString()+"\n Regards");
        taskValidationEmail.setFrom("mchikuruwo@hotmail.com");

        task.setTaskStatus(TaskStatus.ACTIVE);
        task.setTaskProgress(TaskProgress.PARTIAL);

        return new ApiResponse(200,"SUCCESS", taskRequestsService.approveTaskRequest(taskId, dutyId));
    }

    @PutMapping("/reject/{task-id}/{duty-id}")
    @ApiOperation(value="Rejection of a task request. " + "Takes taskRequestId and dutyId as path variables ", response = ApiResponse.class)
    public ApiResponse rejectTaskRequest(@PathVariable("task-id") Long taskId,
                                         @PathVariable("duty-id") Long dutyId){

        TaskRequests taskRequest = taskRequestsService.getOne(taskId);
        taskRequest.setDelegatedDuty(delegationOfDutyService.getOne(dutyId));

        //send task request rejection email, could even be over sms
        SimpleMailMessage taskRejectionEmail = new SimpleMailMessage();
        taskRejectionEmail.setTo(delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo().getEmailAddress());
        taskRejectionEmail.setSubject("Task Request Rejection Alert");
        taskRejectionEmail.setText(" Dear " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo().getName().toUpperCase()+ " " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo().getSurname().toUpperCase() + ",\n Task Request: " + taskRequest.getTaskName() +" to duty: " + delegationOfDutyService.getOne(dutyId).getDuty()
                + " requested to  " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignedBy().getName().toUpperCase() +" " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignedBy().getSurname().toUpperCase()  +" , with description: "+ taskRequest.getDescription()+ "\n Duration from start date: "+taskRequest.getStartDate()+","+" to finish date: "+ taskRequest.getEndDate()+"."+ " has been " + taskRequest.getTaskRequestStatus() +
                "\nKindly look into it and if there are any questions kindly contact  "+ delegationOfDutyService.getOne(dutyId).getEmployeeByAssignedBy().getName().toUpperCase() +" at: " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignedBy().getEmailAddress().toString()+"\n Regards");
        taskRejectionEmail.setFrom("mchikuruwo@hotmail.com");

        taskRequest.setTaskStatus(TaskStatus.PENDING);
        taskRequest.setTaskProgress(TaskProgress.INITIAL); //DEFAULT


        return new ApiResponse(200,"SUCCESS", taskRequestsService.rejectTaskRequest(taskId,dutyId));
    }

    @PutMapping("/complete/{task-id}/{duty-id}")
    @ApiOperation(value="Completion of a requested task . " + "Takes taskRequestId and dutyId as path variables ", response = ApiResponse.class)
    public ApiResponse completionOfTaskRequest(@PathVariable("task-id") Long taskId,
                                         @PathVariable("duty-id") Long dutyId){

        TaskRequests taskRequest = taskRequestsService.getOne(taskId);
        taskRequest.setDelegatedDuty(delegationOfDutyService.getOne(dutyId));

        //send task request rejection email, could even be over sms
        SimpleMailMessage taskCompletionEmail = new SimpleMailMessage();
        taskCompletionEmail.setTo(delegationOfDutyService.getOne(dutyId).getEmployeeByAssignedBy().getEmailAddress());
        taskCompletionEmail.setSubject("Task Request Rejection Alert");
        taskCompletionEmail.setText(" Dear " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignedBy().getName().toUpperCase()+ " " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignedBy().getSurname().toUpperCase() + ",\n Task Request: " + taskRequest.getTaskName() +" to duty: " + delegationOfDutyService.getOne(dutyId).getDuty()
                + " assigned to  " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo().getName().toUpperCase() +" " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo().getSurname().toUpperCase()  +" , with description: "+ taskRequest.getDescription()+ "\n Duration from start date: "+taskRequest.getStartDate()+","+" to finish date: "+ taskRequest.getEndDate()+"."+ " has been " + taskRequest.getTaskProgress() +
                "\nKindly look into it for reviews and if there any gray areas or questions contact  "+ delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo().getName().toUpperCase() +" at: " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo().getEmailAddress().toString()+"\n Regards");
        taskCompletionEmail.setFrom("mchikuruwo@hotmail.com");



        return new ApiResponse(200,"SUCCESS", taskRequestsService.rejectTaskRequest(taskId,dutyId));
    }

    @PutMapping("/verify-completion/{task-id}/{duty-id}")
    @ApiOperation(value="Completion verification of a task request. " + "Takes taskRequestId and dutyId as path variables ", response = ApiResponse.class)
    public ApiResponse completionVerification(@PathVariable("task-id") Long taskId,
                                         @PathVariable("duty-id") Long dutyId){

        TaskRequests taskRequest = taskRequestsService.getOne(taskId);
        taskRequest.setDelegatedDuty(delegationOfDutyService.getOne(dutyId));

        //send task request rejection email, could even be over sms
        SimpleMailMessage taskCompletionVerificationEmail = new SimpleMailMessage();
        taskCompletionVerificationEmail.setTo(delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo().getEmailAddress());
        taskCompletionVerificationEmail.setSubject("Task Request Rejection Alert");
        taskCompletionVerificationEmail.setText(" Dear " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo().getName().toUpperCase()+ " " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo().getSurname().toUpperCase() + ",\n Task Request: " + taskRequest.getTaskName() +" to duty: " + delegationOfDutyService.getOne(dutyId).getDuty()
                + " assigned to  " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo().getName().toUpperCase() +" " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo().getSurname().toUpperCase()  +" , with description: "+ taskRequest.getDescription()+ "\n Duration from start date: "+taskRequest.getStartDate()+","+" to finish date: "+ taskRequest.getEndDate()+"."+ " completion has been APPROVED" +
                "\nKindly look into it and if there are any more editions kindly contact  "+ delegationOfDutyService.getOne(dutyId).getEmployeeByAssignedBy().getName().toUpperCase() +" at: " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignedBy().getEmailAddress().toString()+"\n Regards");
        taskCompletionVerificationEmail.setFrom("mchikuruwo@hotmail.com");


        return new ApiResponse(200,"SUCCESS", taskRequestsService.rejectTaskRequest(taskId,dutyId));
    }

    @PutMapping("/reject-completion/{task-id}/{duty-id}")
    @ApiOperation(value="Rejection of a task request completion claim. " + "Takes taskRequestId and dutyId as path variables ", response = ApiResponse.class)
    public ApiResponse rejectTaskRequestCompletion(@PathVariable("task-id") Long taskId,
                                         @PathVariable("duty-id") Long dutyId){

        TaskRequests taskRequest = taskRequestsService.getOne(taskId);
        taskRequest.setDelegatedDuty(delegationOfDutyService.getOne(dutyId));

        //send task request rejection email, could even be over sms
        SimpleMailMessage taskRejectionEmail = new SimpleMailMessage();
        taskRejectionEmail.setTo(delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo().getEmailAddress());
        taskRejectionEmail.setSubject("Task Request Completion Rejection Alert");
        taskRejectionEmail.setText(" Dear " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo().getName().toUpperCase()+ " " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignTo().getSurname().toUpperCase() + ",\n Task Request: " + taskRequest.getTaskName() +" to duty: " + delegationOfDutyService.getOne(dutyId).getDuty()
                + " approved by  " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignedBy().getName().toUpperCase() +" " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignedBy().getSurname().toUpperCase()  +" , with description: "+ taskRequest.getDescription()+ "\n Duration from start date: "+taskRequest.getStartDate()+","+" to finish date: "+ taskRequest.getEndDate()+"."+ "completion approval has been REJECTED"  +
                "\nKindly look into it and if there are any questions kindly contact  "+ delegationOfDutyService.getOne(dutyId).getEmployeeByAssignedBy().getName().toUpperCase() +" at: " + delegationOfDutyService.getOne(dutyId).getEmployeeByAssignedBy().getEmailAddress().toString()+"\n Regards");
        taskRejectionEmail.setFrom("mchikuruwo@hotmail.com");

        return new ApiResponse(200,"SUCCESS", taskRequestsService.rejectTaskRequest(taskId,dutyId));
    }
}

