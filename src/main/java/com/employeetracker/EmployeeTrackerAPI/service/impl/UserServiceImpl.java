package com.employeetracker.EmployeeTrackerAPI.service.impl;

import com.employeetracker.EmployeeTrackerAPI.dao.RoleRepository;
import com.employeetracker.EmployeeTrackerAPI.dao.UserRepository;
import com.employeetracker.EmployeeTrackerAPI.exceptions.*;
import com.employeetracker.EmployeeTrackerAPI.models.MyUserPrincipal;
import com.employeetracker.EmployeeTrackerAPI.models.Role;
import com.employeetracker.EmployeeTrackerAPI.models.User;
import com.employeetracker.EmployeeTrackerAPI.service.iface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String add(User user) {
        Optional<User> userFromDatabase = Optional.ofNullable(userRepository.findUserByEmailAddress(user.getEmailAddress()));
        if (userFromDatabase.isPresent()) throw new UserAlreadyExistsException("User Already exists!");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User credentials have been successfully sent";
    }

    @Transactional
    @Override
    public String update(User user) {
        Optional<User> userFromDatabase = userRepository.findById(user.getId());
        if (!userFromDatabase.isPresent()) throw new UserNotFoundException("User does not exist");
        // Carry date created timestamp
        user.setDateCreated(userFromDatabase.get().getDateCreated());
        userRepository.save(user);
        return "User with ID " + user.getId() + " has been updated";
    }

    @Override
    public String reset(User user) {
        Optional<User> userFromDatabase = Optional.ofNullable(userRepository.findUserByEmailAddress(user.getEmailAddress()));
        if (!userFromDatabase.isPresent()) throw new EmailNotFoundException("User Email does not exist!");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User credentials have been successfully reset, check email.";
    }

    @Transactional
    @Override
    public String delete(Integer id) {
        Optional<User> userToDelete = userRepository.findById(id);
        if (!userToDelete.isPresent()){
            throw new UserNotFoundException("User with ID " + id + " does not exist");
        }
        userRepository.deleteById(id);
        return "User has been deleted";

    }

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()){
            throw new UsersNotAvailableException("Users not found");
        }
        return users;
    }

    @Override
    public User getOne(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()){
            throw new UserNotFoundException("User with the ID " + id + " does not exist");
        }
        return user.get();
    }


    @Override
    public User authUser(String emailAddress, String password) throws Exception {
        //First get the user by email to check if the user exists
        User user = userRepository.findUserByEmailAddress(emailAddress);
        if (user == null){
            //Display an error that the user with the email address was not found
            throw new EntityNotFoundException("User with email: " + emailAddress + " not found");
        }
        //Check user entered password if it matches hashed password in database
        if (!passwordEncoder.matches(password, user.getPassword())){
            throw new BadCredentialsException("Incorrect password");
        }
        //Else return the user if found
        return user;
    }

     /*@Override
    public User empCodeAuth(String employeeCode, String password) throws Exception{
        //First get the user by employee code to check if the user exists
        User user = userRepository.findUserByEmployeeCode(employeeCode);
        if (user == null){
            //Display an error that the user with the employee code was not found
            throw new EntityNotFoundException("User with employeeCode: " + employeeCode + " not found");
        }
        //Check user entered password if it matches hashed password in database
        if (!passwordEncoder.matches(password, user.getPassword())){
            throw new BadCredentialsException("Incorrect password");
        }
        //Else return the user if found
        return user;
    }

      */

    @Override
    public User findByEmailAddress(String emailAddress) {
        User user = userRepository.findUserByEmailAddress(emailAddress);
        if (user == null){
            throw new EmailNotFoundException("User with the email " + emailAddress + " not found");
        }
        return user;
    }

    @Override
    public User findByEmployeeCode(String employeeCode) {
        User user = userRepository.findUserByEmployeeCode(employeeCode);
        if (user == null){
            throw new EmployeeCodeNotFoundException("User with the employeeCode " + employeeCode + " not found");
        }
        return user;
    }


    @Transactional
    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmailAddress(emailAddress);
        roleRepository.findByName(user.getRole().toString());
        Set<GrantedAuthority> grantedAuthorities = null;
        try {
            user = userRepository.findUserByEmailAddress(emailAddress);
            if (user == null)
                throw new UsernameNotFoundException("User with email:" + emailAddress + " not available.");

            grantedAuthorities = new HashSet<>();
            for (Role role : user.getRole()) {
                String roleName = "ROLE_" + role.getName();
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(roleName);
                grantedAuthorities.add(grantedAuthority);
            }
        }
        catch(Exception exp) {
            exp.printStackTrace();
        }
       // return new org.springframework.security.core.userdetails.User(user , user.getPassword(), grantedAuthorities);
        if (user == null){
            //TODO: Set an error that the user by that email address cannot be found
            throw new UsernameNotFoundException("Could not find the user " + emailAddress);

        }

        return new MyUserPrincipal(user,user.getPassword(),grantedAuthorities);
    }


}
