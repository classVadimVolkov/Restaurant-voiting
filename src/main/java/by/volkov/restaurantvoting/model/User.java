package by.volkov.restaurantvoting.model;

import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true, exclude = {"password"})
public class User extends BaseEntity {
    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotEmpty
    @Size(max = 120)
    private String email;

    @Column(name = "first_name")
    @Size(max = 128)
    private String firstName;

    @Column(name = "last_name")
    @Size(max = 128)
    private String lastName;

    @Column(name = "password")
    @Size(max = 256)
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @ElementCollection(fetch = FetchType.EAGER)
    @BatchSize(size = 20)
    private Set<Role> roles;

    @Column(name = "voted", nullable = false, columnDefinition = "bool default false")
    private boolean voted;
}
