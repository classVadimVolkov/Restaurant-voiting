package by.volkov.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @OneToMany(mappedBy = "restaurant")
    @JsonIgnore
    private Set<Dish> dishes;
}
