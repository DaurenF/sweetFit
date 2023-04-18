package kz.sweet.fit.models;

import jakarta.persistence.*;
import kz.sweet.fit.models.enums.Muscle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "exercises", indexes = {
        @Index(name = "exercise_name_index",  columnList="name", unique = true)
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name ="description")
    private String description;
    @Column(name = "main_muscle")
    private Muscle mainMuscle;
    public Exercise(String name, String description, Muscle mainMuscle) {
        this.name = name;
        this.description = description;
        this.mainMuscle = mainMuscle;
    }
}
