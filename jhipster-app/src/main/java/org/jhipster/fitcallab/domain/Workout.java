package org.jhipster.fitcallab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Workout.
 */
@Entity
@Table(name = "workout")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Workout implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "type", length = 20, nullable = false)
    private String type;

    @NotNull
    @Min(value = 1)
    @Max(value = 120)
    @Column(name = "minutes", nullable = false)
    private Integer minutes;

    @ManyToOne
    @JsonIgnoreProperties(value = "workouts", allowSetters = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public Workout type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public Workout minutes(Integer minutes) {
        this.minutes = minutes;
        return this;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public User getUser() {
        return user;
    }

    public Workout user(User user) {
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
        if (!(o instanceof Workout)) {
            return false;
        }
        return id != null && id.equals(((Workout) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Workout{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", minutes=" + getMinutes() +
            "}";
    }
}
