package kz.sweet.fit.controllers;
import kz.sweet.fit.models.Exercise;
import kz.sweet.fit.models.enums.Muscle;
import kz.sweet.fit.services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/exercise")
public class ExerciseController {
    @Autowired
    ExerciseService exerciseService;

    @GetMapping("/all")
    public ResponseEntity<List<Exercise>> getAll(){
        return ResponseEntity.ok(exerciseService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getById(@PathVariable Long id){
        return ResponseEntity.ok(exerciseService.getById(id));
    }

    @GetMapping("/get-by-muscle-group/{muscleGroup}")
    public ResponseEntity<List<Exercise>> getByMuscleGroup(@PathVariable Muscle muscleGroup){
        return ResponseEntity.ok(exerciseService.getByMuscleGroup(muscleGroup));
    }

    @GetMapping("/get-classic-set/{mainMuscle}")
    public ResponseEntity<Set<Exercise>> getClassicSet(@PathVariable Muscle mainMuscle){
        return ResponseEntity.ok(exerciseService.getClassicSetByMainMuscle(mainMuscle));
    }





    @GetMapping("/init")
    public ResponseEntity<String> init(){
        exerciseService.initExercises();
        return ResponseEntity.ok("Ok");
    }
}
