package by.volkov.restaurantvoting.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "dish")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
public class Dish extends BaseEntity {

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 150, message = "Length of name should be from 2 to 150 characters")
    private String name;

    @Column(name = "price")
    private int price;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;
}
