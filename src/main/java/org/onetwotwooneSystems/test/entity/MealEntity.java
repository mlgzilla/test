package org.onetwotwooneSystems.test.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "meals")
public class MealEntity {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private Integer calories;

    @NotNull
    private Integer proteins;

    @NotNull
    private Integer fats;

    @NotNull
    private Integer carbohydrates;

    @OneToMany(mappedBy = "mealEntity")
    private Set<MealsHistoryEntity> mealsHistoryEntity = new HashSet<>();
}
