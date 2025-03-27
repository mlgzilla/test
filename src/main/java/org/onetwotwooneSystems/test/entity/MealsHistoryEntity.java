package org.onetwotwooneSystems.test.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "meals_history")
public class MealsHistoryEntity {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "meal_id")
    private MealEntity mealEntity;

    @NotNull
    @Column(name = "meal_amount")
    private Integer mealAmount;

    @NotNull
    @Column(name = "date_time")
    private LocalDateTime dateTime;
}
