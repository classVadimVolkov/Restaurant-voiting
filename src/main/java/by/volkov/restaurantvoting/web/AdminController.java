package by.volkov.restaurantvoting.web;

import by.volkov.restaurantvoting.AuthUser;
import by.volkov.restaurantvoting.model.Role;
import by.volkov.restaurantvoting.model.User;
import by.volkov.restaurantvoting.repository.UserRepository;
import by.volkov.restaurantvoting.util.ValidationUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/admin/users")
@Slf4j
@AllArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        log.info("get all {}", users);
        return users;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> getUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        log.info("get {}", user);
        return ValidationUtil.checkNotFoundWithId(user, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int id) {
        log.info("delete User with id={}", id);
        ValidationUtil.checkNotFoundWithId(userRepository.delete(id), id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        log.info("create {}", user);
        ValidationUtil.checkNew(user);
        user.setRoles(Set.of(Role.USER));
        user = userRepository.save(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(user.id()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(user);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@Valid @RequestBody User user, @PathVariable int id) {
        log.info("update {} with id={}", user, id);
        AuthUser authUser = (AuthUser) userDetailsService.loadUserByUsername(userRepository.findById(id).get().getEmail());
        User oldUser = authUser.getUser();
        ValidationUtil.assureIdConsistent(user, id);
        user.setRoles(oldUser.getRoles());
        if (user.getPassword() == null) {
            user.setPassword(oldUser.getPassword());
        }
        log.info("updated {} with id={}", user, id);
        ValidationUtil.checkNotFoundWithId(userRepository.save(user), id);
    }

    @GetMapping(value = "/search/by-email", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<User> getUserByEmail(@RequestParam String email) {
        log.info("get User by email {}", email);
        return ValidationUtil.checkNotFound(userRepository.findByEmailIgnoreCase(email), "email=" + email);
    }
}
