package by.volkov.restaurantvoting;

import by.volkov.restaurantvoting.model.User;
import lombok.Getter;
import org.springframework.lang.NonNull;

@Getter
public class AuthUser extends org.springframework.security.core.userdetails.User {
    @NonNull
    private final User user;

    public AuthUser(User user) {
        super(user.getEmail(), user.getPassword(), user.getRoles());
        this.user = user;
    }

    public int id() {
        return user.id();
    }
}
