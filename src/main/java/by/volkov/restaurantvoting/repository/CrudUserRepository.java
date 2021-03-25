package by.volkov.restaurantvoting.repository;

import by.volkov.restaurantvoting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CrudUserRepository extends JpaRepository<User, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(int id);

    User getByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.voted=true, u.votedRestaurantId=:votedRestaurantId WHERE u.id=:id")
    User vote(int id, int votedRestaurantId);

    @Query("SELECT u.voted FROM User u WHERE u.id=:id")
    boolean hasVoted(int id);
}
