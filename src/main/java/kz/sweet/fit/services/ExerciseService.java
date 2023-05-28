package kz.sweet.fit.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.sweet.fit.exceptions.CouldNotCreateExerciseException;
import kz.sweet.fit.models.dto.ExerciseDto;
import kz.sweet.fit.models.entity.ExerciseEntity;
import kz.sweet.fit.models.entity.TechniqueEntity;
import kz.sweet.fit.models.enums.Muscle;
import kz.sweet.fit.repositories.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ObjectMapper objectMapper;

    public List<ExerciseEntity> getAll() {
        return exerciseRepository.findAll();
    }

    public ExerciseEntity getById(Long id) {
        return exerciseRepository.findById(id).orElse(null);
    }

    public List<ExerciseEntity> getByMuscleGroup(Muscle muscle) {
        return exerciseRepository.findAllByMainMuscle(muscle);
    }

    public Set<ExerciseEntity> getClassicSetByMainMuscle(Muscle mainMuscle) {
        Muscle secondaryMuscle = this.defineSecondaryMuscle(mainMuscle);
        List<ExerciseEntity> mainList = exerciseRepository.findTop3Rand(mainMuscle.ordinal());

        List<ExerciseEntity> secondaryList = exerciseRepository.findTop2Rand(secondaryMuscle.ordinal());
        Set<ExerciseEntity> set = new HashSet<>(mainList);
        set.addAll(secondaryList);
        return set;
    }

    public ExerciseEntity save(ExerciseDto newExercise){
        try{
            TechniqueEntity technique = new TechniqueEntity(newExercise.getTechnique());
            ExerciseEntity exerciseEntity = new ExerciseEntity(newExercise.getName(), newExercise.getDescription(), newExercise.getMainMuscle(), technique);
            return exerciseRepository.save(exerciseEntity);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new CouldNotCreateExerciseException("Could not create exercise", 500);
        }
    }

    private Muscle defineSecondaryMuscle(Muscle muscle) {
        if (muscle == Muscle.CHEST) return Muscle.BICEPS;
        else if (muscle == Muscle.LEGS) return Muscle.SHOULDERS;
        else return Muscle.TRICEPS;
    }

}
