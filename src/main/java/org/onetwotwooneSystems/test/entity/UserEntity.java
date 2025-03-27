package org.onetwotwooneSystems.test.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.onetwotwooneSystems.test.enums.UserTarget;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private String name;

    private String email;

    @NotNull
    private Integer age;

    @NotNull
    private Integer weight;

    @NotNull
    private Integer height;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserTarget target;

    @NotNull
    @Column(name = "daily_target")
    private Integer dailyTarget;

    @OneToMany(mappedBy = "userEntity")
    private Set<MealsHistoryEntity> mealsHistory = new HashSet<>();
}
