package com.employeetracker.EmployeeTrackerAPI.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "location_login", schema = "employee_tracker")
public class LocationLogin {
    private Long id;
    private Timestamp date;
    private Location locationByLocationId;
    private Login loginByLoginId;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationLogin that = (LocationLogin) o;
        return Objects.equals(id, that.id) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date);
    }

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
    public Location getLocationByLocationId() {
        return locationByLocationId;
    }

    public void setLocationByLocationId(Location locationByLocationId) {
        this.locationByLocationId = locationByLocationId;
    }

    @ManyToOne
    @JoinColumn(name = "login_id", referencedColumnName = "id", nullable = false)
    public Login getLoginByLoginId() {
        return loginByLoginId;
    }

    public void setLoginByLoginId(Login loginByLoginId) {
        this.loginByLoginId = loginByLoginId;
    }
}
