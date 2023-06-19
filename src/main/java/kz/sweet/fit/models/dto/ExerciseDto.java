package kz.sweet.fit.models.dto;

import kz.sweet.fit.models.enums.Muscle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ExerciseDto {
    private Long id;
    private String name;
    private String description;
    private Muscle mainMuscle;
    private String technique;
}
