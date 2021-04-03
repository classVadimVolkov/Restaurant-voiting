package by.volkov.restaurantvoting.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
public class Restaurant extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 100, message = "Length of name should be from 2 to 100 characters")
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @OneToMany(mappedBy = "restaurant")
    private Set<Dish> dishes;
}
