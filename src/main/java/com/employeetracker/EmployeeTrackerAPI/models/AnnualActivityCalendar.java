package com.employeetracker.EmployeeTrackerAPI.models;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "annual_activity_calendar", schema = "employee_tracker")
public class AnnualActivityCalendar {
    private Long id;
    private String activity;
    private Date activityDay;
    private Timestamp dateUpdated;
    private AnnualActivityCategory activityCategory;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "activity")
    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    @Basic
    @Column(name = "activity_day")
    public Date getActivityDay() {
        return activityDay;
    }

    public void setActivityDay(Date activityDay) {
        this.activityDay = activityDay;
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
        AnnualActivityCalendar that = (AnnualActivityCalendar) o;
        return Objects.equals(id, that.id) && Objects.equals(activity, that.activity) && Objects.equals(activityDay, that.activityDay) && Objects.equals(dateUpdated, that.dateUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, activity, activityDay, dateUpdated);
    }

    @ManyToOne
    @JoinColumn(name = "annual_activity_category_id", referencedColumnName = "id", nullable = false)
    public AnnualActivityCategory getActivityCategory() {
        return activityCategory;
    }

    public void setActivityCategory(AnnualActivityCategory annualActivityCategory) {
        this.activityCategory = annualActivityCategory;
    }
}
