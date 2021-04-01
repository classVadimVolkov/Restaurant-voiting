package by.volkov.restaurantvoting.repository;

import by.volkov.restaurantvoting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(int id);

    @RestResource(rel = "by-email", path = "by-email")
    Optional<User> getByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.voted=:voted, u.votedRestaurantId=:votedRestaurantId WHERE u.id=:id")
    User vote(boolean voted, int id, int votedRestaurantId);

    @Query("SELECT u.voted FROM User u WHERE u.id=:id")
    boolean hasVoted(int id);
}
