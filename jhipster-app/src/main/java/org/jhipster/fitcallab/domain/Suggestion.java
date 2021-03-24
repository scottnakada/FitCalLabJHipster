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
 * A Suggestion.
 */
@Entity
@Table(name = "suggestion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Suggestion implements Serializable {

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
    @Size(max = 200)
    @Column(name = "suggestion", length = 200, nullable = false)
    private String suggestion;

    @ManyToOne
    @JsonIgnoreProperties(value = "suggestions", allowSetters = true)
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

    public Suggestion dayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Month getMonth() {
        return month;
    }

    public Suggestion month(Month month) {
        this.month = month;
        return this;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public Suggestion day(Integer day) {
        this.day = day;
        return this;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public Suggestion startTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public Suggestion suggestion(String suggestion) {
        this.suggestion = suggestion;
        return this;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public User getUser() {
        return user;
    }

    public Suggestion user(User user) {
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
        if (!(o instanceof Suggestion)) {
            return false;
        }
        return id != null && id.equals(((Suggestion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Suggestion{" +
            "id=" + getId() +
            ", dayOfWeek='" + getDayOfWeek() + "'" +
            ", month='" + getMonth() + "'" +
            ", day=" + getDay() +
            ", startTime='" + getStartTime() + "'" +
            ", suggestion='" + getSuggestion() + "'" +
            "}";
    }
}
