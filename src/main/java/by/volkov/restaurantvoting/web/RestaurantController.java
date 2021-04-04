package by.volkov.restaurantvoting.web;

import by.volkov.restaurantvoting.AuthUser;
import by.volkov.restaurantvoting.model.Restaurant;
import by.volkov.restaurantvoting.repository.RestaurantRepository;
import by.volkov.restaurantvoting.util.ValidationUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/restaurants")
@Slf4j
@AllArgsConstructor
public class RestaurantController {

    private final RestaurantRepository restaurantRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        log.info("get all {}", restaurants);
        return restaurants;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Restaurant> getRestaurant(@PathVariable int id) {
        log.info("get Restaurant with id={}", id);
        return ValidationUtil.checkNotFoundWithId(restaurantRepository.findById(id), id);
    }

    @GetMapping(value = "/search/by-name", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Restaurant> getByName(@RequestParam String name) {
        log.info("get Restaurant by name {}", name);
        return ValidationUtil.checkNotFound(restaurantRepository.getByName(name), "name=" + name);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRestaurant(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("delete Restaurant with id={}", id);
        ValidationUtil.checkNotFoundWithId(restaurantRepository.delete(id, authUser.id()), id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Restaurant> createRestaurant(@AuthenticationPrincipal AuthUser authUser,
                                                       @Valid @RequestBody Restaurant restaurant) {
        log.info("create {}", restaurant);
        ValidationUtil.checkNew(restaurant);
        restaurant.setUser(authUser.getUser());
        restaurant = restaurantRepository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(restaurant.id()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(restaurant);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRestaurant(@AuthenticationPrincipal AuthUser authUser,
                                 @Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("update {} with id={}", restaurant, id);
        ValidationUtil.assureIdConsistent(restaurant, id);
        restaurant.setUser(authUser.getUser());
        if (restaurant.getDishes() == null) {
            restaurant.setDishes(restaurantRepository.getOne(id).getDishes());
        }
        log.info("updated {} with id={}", restaurant, id);
        ValidationUtil.checkNotFoundWithId(restaurantRepository.save(restaurant), id);
    }
}
