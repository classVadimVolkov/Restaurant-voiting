package by.volkov.restaurantvoting.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Restaurant extends BaseEntity {
    private String dishName;

    private int price;

    private User user;
}
