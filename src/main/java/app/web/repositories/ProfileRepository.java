package app.web.repositories;

import app.web.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProfileRepository extends JpaRepository<Profile, String> {
    @Modifying
    @Transactional
    @Query("UPDATE Profile p SET p.imageUrl = :imageUrl WHERE p.username = :username")
    void updateProfilePictureUrl(@Param("imageUrl") String imageUrl, @Param("username") String username);

    @Modifying
    @Transactional
    @Query("UPDATE Profile p SET p.password = :password WHERE p.username = :username")
    void updatePassword(@Param("password") String password, @Param("username") String username);

}
