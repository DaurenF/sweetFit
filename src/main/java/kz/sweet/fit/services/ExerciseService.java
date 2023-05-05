package kz.sweet.fit.services;

import kz.sweet.fit.models.Exercise;
import kz.sweet.fit.models.enums.Muscle;
import kz.sweet.fit.repositories.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public List<Exercise> getAll() {
        return exerciseRepository.findAll();
    }

    public Exercise getById(Long id) {
        return exerciseRepository.findById(id).orElse(null);
    }

    public List<Exercise> getByMuscleGroup(Muscle muscle) {
        return exerciseRepository.findAllByMainMuscle(muscle);
    }

    public Set<Exercise> getClassicSetByMainMuscle(Muscle mainMuscle) {
        Muscle secondaryMuscle = this.defineSecondaryMuscle(mainMuscle);
        List<Exercise> mainList = exerciseRepository.findTop3Rand(mainMuscle.ordinal());

        List<Exercise> secondaryList = exerciseRepository.findTop2Rand(secondaryMuscle.ordinal());
        Set<Exercise> set = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            set.add(mainList.get(i));
        }
        for (int i = 0; i < 2; i++) {
            set.add(secondaryList.get(i));
        }
        return set;
    }

    private Muscle defineSecondaryMuscle(Muscle muscle) {
        if (muscle == Muscle.CHEST) return Muscle.BICEPS;
        else if (muscle == Muscle.LEGS) return Muscle.SHOULDERS;
        else return Muscle.TRICEPS;
    }

}
