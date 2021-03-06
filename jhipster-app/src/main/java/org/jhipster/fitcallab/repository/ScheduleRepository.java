package org.jhipster.fitcallab.repository;

import org.jhipster.fitcallab.domain.Schedule;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Schedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("select schedule from Schedule schedule where schedule.user.login = ?#{principal.username}")
    List<Schedule> findByUserIsCurrentUser();
}
