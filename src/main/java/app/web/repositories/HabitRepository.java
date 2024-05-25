package app.web.repositories;

import app.web.models.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HabitRepository extends JpaRepository<Habit, Long> {

    @Query("SELECT h FROM Habit h WHERE h.profile.username = :username")
    List<Habit> findAllHabitsPerUsername(@Param("username") String username);

    @Query("SELECT h FROM Habit h WHERE h.isActive = true AND h.profile.username = :username")
    List<Habit> findAllActiveHabitsPerUsername(@Param("username") String username);

    @Modifying
    @Transactional
    @Query("UPDATE Habit e SET e.isActive = false WHERE e.id = :id")
    void deactivateHabit(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Habit e SET e.isActive = true WHERE e.id = :id")
    void activateHabit(@Param("id") Long id);
}
