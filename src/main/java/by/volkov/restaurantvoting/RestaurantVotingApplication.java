package by.volkov.restaurantvoting;

import by.volkov.restaurantvoting.repository.CrudUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class RestaurantVotingApplication implements ApplicationRunner {
    private final CrudUserRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(RestaurantVotingApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(repository.hasVoted(1));
        System.out.println(repository.getByEmail("user@gmail.com"));
        System.out.println(repository.findAll());
    }
}
