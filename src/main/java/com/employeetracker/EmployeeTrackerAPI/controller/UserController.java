package com.employeetracker.EmployeeTrackerAPI.controller;

import com.employeetracker.EmployeeTrackerAPI.api.config.ApiResponse;
import com.employeetracker.EmployeeTrackerAPI.dto.AddUserDto;
import com.employeetracker.EmployeeTrackerAPI.dto.GenerateCredentialsDto;
import com.employeetracker.EmployeeTrackerAPI.dto.LoginDto;
import com.employeetracker.EmployeeTrackerAPI.dto.UpdateUserDto;
import com.employeetracker.EmployeeTrackerAPI.models.Login;
import com.employeetracker.EmployeeTrackerAPI.models.User;
import com.employeetracker.EmployeeTrackerAPI.security.JwtTokenProvider;
import com.employeetracker.EmployeeTrackerAPI.service.iface.EmailService;
import com.employeetracker.EmployeeTrackerAPI.service.iface.LoginService;
import com.employeetracker.EmployeeTrackerAPI.service.iface.RoleService;
import com.employeetracker.EmployeeTrackerAPI.service.iface.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Random;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";

    private final UserService userService;
    private final LoginService loginService;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final EmailService emailService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService, LoginService loginService, RoleService roleService, ModelMapper modelMapper, EmailService emailService, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.loginService = loginService;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.emailService = emailService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(value = "/signUp/{role-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Sign up a user to the AEMAPS platform with role-id as path variable", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse signUpUser(@RequestBody AddUserDto addUserDto, @PathVariable("role-id") Integer roleId, HttpServletRequest request){
        User user = modelMapper.map(addUserDto, User.class);

        // Assign the role of the user
        user.setRoles(Collections.singleton(roleService.getOne(roleId)));

        // Generate the password
        String password = generatePassword(user.getName());

        // Set the user password to the generated password
         user.setPassword(password);

        // Set user active true by default
        user.setActive(true);

        // Send a confirmation email message
        String appUrl = request.getScheme() + "://" + request.getServerName() + request.getContextPath();

        SimpleMailMessage registrationEmail = new SimpleMailMessage();
        registrationEmail.setTo(user.getEmailAddress());
        registrationEmail.setSubject("AEMAPS Platform");
        registrationEmail.setText(" Dear " + user.getName() + ", \n You have been registered as a  " + roleService.getOne(roleId).getName()
                + " of the AEMAPS Platform.\n Email: " + user.getEmailAddress() +"\n Employee Code: "+user.getEmployeeCode() +"\n Password: " + password +
                " \n Please keep your temporary password safe and reset it as soon as possible.");
        registrationEmail.setFrom("nust.innovationhub@hotmail.com");

        emailService.sendEmail(registrationEmail);

        return new ApiResponse(200,  "SUCCESS", userService.add(user));
    }

    private String generatePassword(String username) {
        String generatedPassword;
        // Generate random number to append to user's first name
        Random randomNumber = new Random();
        int n = 1000 + randomNumber.nextInt(9999);
        // Concatenate the random number and first name
        generatedPassword = username.concat(String.valueOf(n));
        return generatedPassword;
    }


    @PostMapping(value = "/login/email")
    @ApiOperation("Enables a user to login with company email address and password")
    public ResponseEntity loginWithEmailAndPassword(@RequestBody LoginDto accountCredentials) {
        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(
                        accountCredentials.getEmailAddress(),
                        accountCredentials.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        //Check if the authentication was successful. If it is, then return the details of the user
        ApiResponse response;
        if (authentication.isAuthenticated()){
            User authenticatedUser = userService.findByEmailAddress(accountCredentials.getEmailAddress());

            // Log user login in database
            Login login = new Login();
            login.setUser(authenticatedUser);
            loginService.add(login);

            response = new ApiResponse(200, "SUCCESS", authenticatedUser);
            return ResponseEntity.ok().header(HEADER_STRING, TOKEN_PREFIX + " " + jwt).body(response);
        }
        else {
            response = new ApiResponse(400,"Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.TEXT_PLAIN).body(response);

        }
    }

   /* @PostMapping(value = "/login/empCode")
    @ApiOperation("Enables a user to login with employee code and password")
    public ResponseEntity loginWithEmployeeCodeAndPassword(@RequestBody Login1Dto accountCredentials) {
        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(
                        accountCredentials.getEmployeeCode(),
                        accountCredentials.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        //Check if the authentication was successful. If it is, then return the details of the user
        ApiResponse response;
        if (authentication.isAuthenticated()){
            User authenticatedUser = userService.findByEmployeeCode(accountCredentials.getEmployeeCode());

            // Log user login in database
            Login login = new Login();
            login.setUser(authenticatedUser);
            loginService.add(login);

            response = new ApiResponse(200, "SUCCESS", authenticatedUser);
            return ResponseEntity.ok().header(HEADER_STRING, TOKEN_PREFIX + " " + jwt).body(response);
        }
        else {
            response = new ApiResponse(400,"Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.TEXT_PLAIN).body(response);

        }
    }

    */


    @PutMapping(value = "/edit/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Updates a current user's details", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse updateUser(@RequestBody UpdateUserDto updateUserDto, @PathVariable("id") Integer id){
        User user = modelMapper.map(updateUserDto, User.class);
        return new ApiResponse(200, "SUCCESS", userService.update(user));
    }

    @PostMapping("/generate-credentials")
    @ApiOperation(value = "Generates new password for user.", response = ApiResponse.class)
    public ApiResponse generateCredentialsForUser(@RequestBody GenerateCredentialsDto credentialsDto){
        // Get user by their email address
        User user = userService.findByEmailAddress(credentialsDto.getEmailAddress());

        // Generate the password for user which needs to be reset
        String password = generatePassword(user.getName());

        // Set the user password to the generated password
        user.setPassword(password);

        SimpleMailMessage generatedCredentialsEmail = new SimpleMailMessage();
        generatedCredentialsEmail.setTo(user.getEmailAddress());
        generatedCredentialsEmail.setSubject("AEMAPS-Password Reset");
        generatedCredentialsEmail.setText(" Dear " + user.getName() + " Your new credentials are as follows: \n Email: " + user.getEmailAddress() +"\n Password: " + password +
                ". \n Please keep your temporary password safe and reset it as soon as possible.");
        generatedCredentialsEmail.setFrom("nust.innovationhub@hotmail.com");

        emailService.sendEmail(generatedCredentialsEmail);

        return new ApiResponse(200,  "SUCCESS", userService.add(user));
    }
}
