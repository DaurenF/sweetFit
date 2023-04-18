package kz.sweet.fit.services;

import kz.sweet.fit.models.Exercise;
import kz.sweet.fit.models.enums.Muscle;
import kz.sweet.fit.repositories.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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


    public void initExercises() {
        List<Exercise> list = new ArrayList<>();
        list.add(new Exercise("Squats", "Works legs, glutes, and lower back", Muscle.LEGS));
        list.add(new Exercise("Bench Press", "Works chest, shoulders, and triceps", Muscle.CHEST));
        list.add(new Exercise("Deadlifts", "Works back, legs, and glutes", Muscle.BACK));
        list.add(new Exercise("Lunges", "Works legs, glutes, and hips", Muscle.LEGS));
        list.add(new Exercise("Pullups", "Works back, shoulders, and biceps", Muscle.BACK));
        list.add(new Exercise("Pushups", "Works chest, shoulders, and triceps", Muscle.CHEST));
        list.add( new Exercise("Dips", "Works chest, triceps, and shoulders", Muscle.CHEST));
        list.add(new Exercise("Rows", "Works back, shoulders, and biceps", Muscle.BACK));
        list.add( new Exercise("Shoulder Press", "Works shoulders and triceps", Muscle.SHOULDERS));
        list.add( new Exercise("Bicep Curls", "Works biceps", Muscle.BICEPS));
        list.add(new Exercise("Tricep Extensions", "Works triceps", Muscle.TRICEPS));
        list.add(new Exercise("Leg Curls", "Works hamstrings", Muscle.LEGS));
        list.add( new Exercise("Calf Raises", "Works calves", Muscle.LEGS));
        list.add(new Exercise("Crunches", "Works abs", Muscle.ABS));
        list.add( new Exercise("Planks", "Works abs and lower back", Muscle.ABS));
        list.add(new Exercise("Leg Extensions", "Works quads", Muscle.LEGS));
        list.add( new Exercise("Leg Press", "Works legs and glutes", Muscle.LEGS));
        list.add(new Exercise("Lat Pulldowns", "Works back and biceps", Muscle.BACK));
        list.add( new Exercise("Upright Rows", "Works shoulders and traps", Muscle.SHOULDERS));
        list.add(new Exercise("Bench Dips", "Works triceps and chest", Muscle.TRICEPS));
        list.add( new Exercise("Side Lateral Raises", "Works shoulders and traps", Muscle.SHOULDERS));
        list.add(new Exercise("Reverse Flys", "Works back and rear shoulders", Muscle.BACK));
        list.add(new Exercise("Hammer Curls", "Works biceps and forearms", Muscle.BICEPS));
        list.add( new Exercise("Preacher Curls", "Works biceps", Muscle.BICEPS));
        list.add(new Exercise("Skull Crushers", "Works triceps", Muscle.TRICEPS));
        list.add(new Exercise("Cable Crossovers", "Works chest and shoulders", Muscle.CHEST));
        list.add( new Exercise("Dumbbell Flys", "Works chest", Muscle.CHEST));
        list.add( new Exercise("Incline Bench Press", "Works upper chest, shoulders, and triceps", Muscle.CHEST));
        list.add( new Exercise("Decline Bench Press", "Works lower chest and triceps", Muscle.CHEST));

        for(Exercise exercise : list) {
            exerciseRepository.save(exercise);
        }
    }

}
