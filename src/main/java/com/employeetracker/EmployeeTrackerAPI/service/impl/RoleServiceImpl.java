package com.employeetracker.EmployeeTrackerAPI.service.impl;

import com.employeetracker.EmployeeTrackerAPI.dao.RoleRepository;
import com.employeetracker.EmployeeTrackerAPI.models.Role;
import com.employeetracker.EmployeeTrackerAPI.service.iface.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role getOne(Integer id) {
        Optional<Role> role = roleRepository.findById(id);
        if (!role.isPresent()){
            throw new EntityNotFoundException("Role not found");
        }
        return role.get();
    }

    @Override
    public Role findByName(String name) {
        Role role = roleRepository.findByName(name);
        if (role == null){
            throw new EntityNotFoundException("Role " + name + " not found");
        }
        return role;
    }


}
