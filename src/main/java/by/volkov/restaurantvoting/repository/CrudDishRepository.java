package by.volkov.restaurantvoting.repository;

import by.volkov.restaurantvoting.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudDishRepository extends JpaRepository<Dish, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.restaurant.user.id=:userId AND d.restaurant.id=:restaurantId AND d.id=:id")
    int delete(int id, int restaurantId, int userId);

    @RestResource(rel = "by-restaurantId", path = "by-restaurantId")
    List<Dish> getAllByRestaurantId(int restaurantId);
}