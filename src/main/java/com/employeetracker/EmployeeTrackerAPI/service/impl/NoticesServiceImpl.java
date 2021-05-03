package com.employeetracker.EmployeeTrackerAPI.service.impl;

import com.employeetracker.EmployeeTrackerAPI.dao.NoticesRepository;
import com.employeetracker.EmployeeTrackerAPI.models.Notices;
import com.employeetracker.EmployeeTrackerAPI.service.iface.NoticesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class NoticesServiceImpl implements NoticesService {

    private final NoticesRepository noticesRepository;

    @Autowired
    public NoticesServiceImpl(NoticesRepository noticesRepository) {
        this.noticesRepository = noticesRepository;
    }

    @Override
    public String add(Notices notices) {
        noticesRepository.save(notices);
        return "Notice has been successfully added";
    }

    @Override
    public String update(Notices notice) {
        Optional<Notices> noticeFromDatabase = noticesRepository.findById(notice.getId());
        if (!noticeFromDatabase.isPresent()) throw new EntityNotFoundException("Notice not found!");
        noticesRepository.save(notice);
        return "Notice with ID " + notice.getId() + " has been successfully updated";
    }

    @Override
    public String delete(Long id) {
        Optional<Notices> noticeToDelete = noticesRepository.findById(id);
        if (!noticeToDelete.isPresent()){
            throw new EntityNotFoundException("Notice with ID " + id + " does not exist");
        }
        noticesRepository.deleteById(id);
        return "Notice has been successfully deleted";
    }
    @Override
    public List<Notices> getAll() {
        return noticesRepository.findAll();
    }

    @Override
    public Notices getOne(Long id) {
        Optional<Notices> notice = noticesRepository.findById(id);
        if (!notice.isPresent()){
            throw new EntityNotFoundException("Notice not found!");
        }
        return notice.get();
    }

    @Override
    public Notices findByTitle(String title) {
        Notices notices = noticesRepository.findByTitle(title);
        if (notices == null){
            throw new EntityNotFoundException("Title " + title + " not found");
        }
        return notices;
    }


}

