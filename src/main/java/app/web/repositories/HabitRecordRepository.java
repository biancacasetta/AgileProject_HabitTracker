package app.web.repositories;

import app.web.models.HabitRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface HabitRecordRepository extends JpaRepository<HabitRecord, Long> {

    @Query("SELECT hr FROM HabitRecord hr WHERE hr.completionDate = :date AND hr.habit.profile.username = :username")
    List<HabitRecord> findRecordsAtGivenDate(@Param("date") LocalDate date, @Param("username") String username);

    @Query("SELECT hr FROM HabitRecord hr WHERE hr.completionDate >= :date AND hr.habit.profile.username = :username")
    List<HabitRecord> findRecordsSinceDateUntilToday(@Param("date") LocalDate date, @Param("username") String username);
}

