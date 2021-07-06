package com.employeetracker.EmployeeTrackerAPI.models;

import com.employeetracker.EmployeeTrackerAPI.enums.Gender;
import com.employeetracker.EmployeeTrackerAPI.enums.ResidentialStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.Past;


@Entity
@Table(name = "employee", schema = "employee_tracker")
public class Employee {
    private Integer id;
    private String employeeCode;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String mobileNumber;
    private String emailAddress;
    private ResidentialStatus residentialStatus;
    private String address1;
    private Timestamp dateCreated;
    private Timestamp dateUpdated;
    private EmployeeStatus employeeStatus;
    private JobTitle jobTitle;

    private Set<User> user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Basic
    @Column(name = "employee_code")
    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "date_of_birth")
    @Past(message = "Date should be in the past")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Basic
    @Column(name = "mobile_number")
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Basic
    @Column(name = "email_address")
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "residential_status")
    public ResidentialStatus getResidentialStatus() {
        return residentialStatus;
    }

    public void setResidentialStatus(ResidentialStatus residentialStatus) {
        this.residentialStatus = residentialStatus;
    }

    @Basic
    @Column(name = "address_1")
    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    @Basic
    @CreationTimestamp
    @Column(name = "date_created")
    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Basic
    @UpdateTimestamp
    @Column(name = "date_updated")
    public Timestamp getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Timestamp dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee that = (Employee) o;
        return Objects.equals(id, that.id) && Objects.equals(employeeCode, that.employeeCode) && Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && Objects.equals(gender, that.gender) && Objects.equals(mobileNumber, that.mobileNumber) && Objects.equals(residentialStatus, that.residentialStatus) && Objects.equals(address1, that.address1) && Objects.equals(dateCreated, that.dateCreated) && Objects.equals(dateUpdated, that.dateUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employeeCode, name, surname, gender, mobileNumber, residentialStatus, address1, dateCreated, dateUpdated);
    }

    @ManyToOne
    @JoinColumn(name = "employment_status_id", referencedColumnName = "id", nullable = false)
    public EmployeeStatus getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(EmployeeStatus employeeStatusByEmploymentStatusId) {
        this.employeeStatus = employeeStatusByEmploymentStatusId;
    }

    @ManyToOne
    @JoinColumn(name = "job_title_id", referencedColumnName = "id", nullable = false)
    public JobTitle getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(JobTitle jobTitleByJobTitleId) {
        this.jobTitle = jobTitleByJobTitleId;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "employee_user", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    public Set<User> getUser() {
        return user;
    }

    public void setUser(Set<User> users) {
        this.user = users;
    }
}
