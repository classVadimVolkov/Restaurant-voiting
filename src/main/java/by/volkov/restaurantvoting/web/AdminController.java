package by.volkov.restaurantvoting.web;

import by.volkov.restaurantvoting.model.User;
import by.volkov.restaurantvoting.repository.UserRepository;
import by.volkov.restaurantvoting.util.ValidationUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Slf4j
@AllArgsConstructor
public class AdminController {

    private final UserRepository userRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        log.info("get all {}", users);
        return users;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> get(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        log.info("get {}", user);
        return ValidationUtil.checkNotFoundWithId(user, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete User with id={}", id);
        ValidationUtil.checkNotFoundWithId(userRepository.delete(id), id);
    }

    @GetMapping(value = "/search/by-email", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> getByEmail(@RequestParam String email) {
        log.info("get User by email {}", email);
        return ValidationUtil.checkNotFound(userRepository.findByEmailIgnoreCase(email), "email=" + email);
    }
}
