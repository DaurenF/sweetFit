package kz.sweet.fit.models.entity;

import jakarta.persistence.*;
import kz.sweet.fit.models.enums.Muscle;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "exercises", indexes = {
        @Index(name = "exercise_name_index", columnList = "name", unique = true)
})
@Getter
@Setter
@ToString

public class ExerciseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "main_muscle")
    private Muscle mainMuscle;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "technique_id", referencedColumnName = "id")
    private TechniqueEntity technique;

    public ExerciseEntity(String name, String description, Muscle mainMuscle) {
        this.name = name;
        this.description = description;
        this.mainMuscle = mainMuscle;
    }

    public ExerciseEntity(Long id, String name, String description, Muscle mainMuscle, TechniqueEntity technique) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.mainMuscle = mainMuscle;
        this.technique = technique;
    }

    public ExerciseEntity() {
    }
}
