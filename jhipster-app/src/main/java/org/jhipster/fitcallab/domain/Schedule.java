package org.jhipster.fitcallab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import org.jhipster.fitcallab.domain.enumeration.DayOfWeek;

import org.jhipster.fitcallab.domain.enumeration.Month;

/**
 * A Schedule.
 */
@Entity
@Table(name = "schedule")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "month", nullable = false)
    private Month month;

    @NotNull
    @Min(value = 1)
    @Max(value = 31)
    @Column(name = "day", nullable = false)
    private Integer day;

    @NotNull
    @Size(max = 8)
    @Column(name = "start_time", length = 8, nullable = false)
    private String startTime;

    @NotNull
    @Min(value = 1)
    @Max(value = 120)
    @Column(name = "duration", nullable = false)
    private Integer duration;

    @NotNull
    @Size(max = 100)
    @Column(name = "activity", length = 100, nullable = false)
    private String activity;

    @ManyToOne
    @JsonIgnoreProperties(value = "schedules", allowSetters = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public Schedule dayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Month getMonth() {
        return month;
    }

    public Schedule month(Month month) {
        this.month = month;
        return this;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public Schedule day(Integer day) {
        this.day = day;
        return this;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public Schedule startTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public Schedule duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getActivity() {
        return activity;
    }

    public Schedule activity(String activity) {
        this.activity = activity;
        return this;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public User getUser() {
        return user;
    }

    public Schedule user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Schedule)) {
            return false;
        }
        return id != null && id.equals(((Schedule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Schedule{" +
            "id=" + getId() +
            ", dayOfWeek='" + getDayOfWeek() + "'" +
            ", month='" + getMonth() + "'" +
            ", day=" + getDay() +
            ", startTime='" + getStartTime() + "'" +
            ", duration=" + getDuration() +
            ", activity='" + getActivity() + "'" +
            "}";
    }
}
