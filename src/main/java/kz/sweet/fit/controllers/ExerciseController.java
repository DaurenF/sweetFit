package kz.sweet.fit.controllers;

import kz.sweet.fit.exceptions.BaseExceptionHandler;
import kz.sweet.fit.models.ErrorResponse;
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
@CrossOrigin(origins = "http://localhost:3000" +
        "", allowedHeaders = "*")
public class ExerciseController {

    private final ExerciseService exerciseService;
    private final BaseExceptionHandler exceptionHandler;

    @Autowired
    public ExerciseController(ExerciseService exerciseService, BaseExceptionHandler exceptionHandler) {
        this.exerciseService = exerciseService;
        this.exceptionHandler = exceptionHandler;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Exercise>> getAll() {
        return ResponseEntity.ok(exerciseService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getById(@PathVariable Long id) {
        return ResponseEntity.ok(exerciseService.getById(id));
    }

    @GetMapping("/get-by-muscle-group/{muscleGroup}")
    public ResponseEntity<List<Exercise>> getByMuscleGroup(@PathVariable Muscle muscleGroup) {
        return ResponseEntity.ok(exerciseService.getByMuscleGroup(muscleGroup));
    }
    @GetMapping("/get-classic-set/{mainMuscle}")
    public ResponseEntity<Set<Exercise>> getClassicSet(@PathVariable Muscle mainMuscle) {
        return ResponseEntity.ok(exerciseService.getClassicSetByMainMuscle(mainMuscle));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return exceptionHandler.handleException(e);
    }

    @GetMapping("/init")
    public ResponseEntity<String> init() {
        exerciseService.initExercises();
        return ResponseEntity.ok("Ok");
    }
}
