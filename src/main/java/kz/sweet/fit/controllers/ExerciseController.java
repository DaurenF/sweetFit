package kz.sweet.fit.controllers;

import kz.sweet.fit.exceptions.BaseExceptionHandler;
import kz.sweet.fit.models.dto.ErrorResponse;
import kz.sweet.fit.models.Exercise;
import kz.sweet.fit.models.enums.Muscle;
import kz.sweet.fit.services.ExerciseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/exercise")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
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
        log.info("Client requiest to /exercise/all ");
        return ResponseEntity.ok(exerciseService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getById(@PathVariable Long id) {
        log.info("Client requiest to /exercise/{} ", id);
        return ResponseEntity.ok(exerciseService.getById(id));
    }

    @GetMapping("/get-by-muscle-group/{muscleGroup}")
    public ResponseEntity<List<Exercise>> getByMuscleGroup(@PathVariable Muscle muscleGroup) {
        log.info("Client requiest to /exercise/{}", muscleGroup);
        return ResponseEntity.ok(exerciseService.getByMuscleGroup(muscleGroup));
    }
    @GetMapping("/get-classic-set/{mainMuscle}")
    public ResponseEntity<Set<Exercise>> getClassicSet(@PathVariable Muscle mainMuscle) {
        log.info("Client requiest to /exercise/{}", mainMuscle);
        return ResponseEntity.ok(exerciseService.getClassicSetByMainMuscle(mainMuscle));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return exceptionHandler.handleException(e);
    }

}
