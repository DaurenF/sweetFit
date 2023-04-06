package kz.sweet.fit.services;

import kz.sweet.fit.models.Exercise;
import kz.sweet.fit.models.enums.Muscle;
import kz.sweet.fit.repositories.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExerciseService {
    @Autowired
    ExerciseRepository exerciseRepository;

    public List<Exercise> getAll(){
        return exerciseRepository.findAll();
    }

    public Exercise getById(Long id){
        return exerciseRepository.findById(id).orElse(null);
    }

    public List<Exercise> getByMuscleGroup(Muscle muscle){
        return exerciseRepository.findAllByMainMuscle(muscle);
    }

    public Set<Exercise> getClassicSetByMainMuscle(Muscle mainMuscle){
        Muscle secondaryMuscle = this.defineSecondaryMuscle(mainMuscle);
        List<Exercise> mainList = exerciseRepository.findAllByMainMuscle(mainMuscle);
        List<Exercise> secondaryList = exerciseRepository.findAllByMainMuscle(secondaryMuscle);

        Set<Exercise> set = new HashSet<>();
        Random random = new Random();
        // Get 3 random elements from list1
        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(mainList.size());
            set.add(mainList.get(index));
        }
        // Get 2 random elements from list2
        for (int i = 0; i < 2; i++) {
            int index = random.nextInt(secondaryList.size());
            set.add(secondaryList.get(index));
        }
        return set;
    }

    private Muscle defineSecondaryMuscle(Muscle muscle){
        if(muscle == Muscle.CHEST) return Muscle.BICEPS;
        else if(muscle == Muscle.LEGS) return Muscle.SHOULDERS;
        else return Muscle.TRICEPS;
    }


    public void initExercises(){
        Exercise benchPress = new Exercise("Bench press", "The bench press is a popular strength-training exercise that targets the chest, shoulders, and triceps. Here are some technique tips and advice to help you perform the bench press effectively and safely:\n", Muscle.CHEST);
        Exercise deadLifts = new Exercise("Dead Lifts","Deadlifts are another compound exercise that work your entire body, particularly your back, glutes, and legs." , Muscle.BACK);
        Exercise squat = new Exercise("Squat", "Squats work multiple muscle groups, including your quads, glutes, and hamstrings. They also help build core strength.", Muscle.LEGS);
        Exercise chest2 = new Exercise("Chest2", "Chest 2 exercise", Muscle.CHEST);
        Exercise chest3 = new Exercise("Chest3", "Chest 3 exercise", Muscle.CHEST);
        Exercise chest4 = new Exercise("Chest4", "Chest 4 exercise", Muscle.CHEST);
        Exercise chest5 = new Exercise("Chest5" , "Chest 5 exercise", Muscle.CHEST);
        Exercise chest6 = new Exercise("Chest6", "Chest 6 exercise", Muscle.CHEST);
        Exercise chest7 = new Exercise("Chest7", "Chest 7 exercise", Muscle.CHEST);
        Exercise chest8 = new Exercise("Chest8", "Chest 8 exercise", Muscle.CHEST);
        Exercise chest9 = new Exercise("Chest9", "Chest 9 exercise", Muscle.CHEST);
        Exercise chest10 = new Exercise("Chest10", "Chest 10 exercise", Muscle.CHEST);

        Exercise bicep1 = new Exercise("Bicep1", "Bicep 1 exercise", Muscle.BICEPS);
        Exercise bicep2 = new Exercise("Bicep2", "Bicep 2 exercise", Muscle.BICEPS);
        Exercise bicep3 = new Exercise("Bicep3", "Bicep 3 exercise", Muscle.BICEPS);
        Exercise bicep4 = new Exercise("Bicep4", "Bicep 4 exercise", Muscle.BICEPS);
        Exercise bicep5 = new Exercise("Bicept5", "Bicep 5 exercise", Muscle.BICEPS);
        Exercise bicep6 = new Exercise("Bicep6", "Bicep 6 exercise", Muscle.BICEPS);

        exerciseRepository.save(chest2);
        exerciseRepository.save(chest3);
        exerciseRepository.save(chest4);
        exerciseRepository.save(chest5);
        exerciseRepository.save(chest6);
        exerciseRepository.save(chest7);
        exerciseRepository.save(chest8);
        exerciseRepository.save(chest9);
        exerciseRepository.save(chest10);

        exerciseRepository.save(bicep1);
        exerciseRepository.save(bicep2);
        exerciseRepository.save(bicep3);
        exerciseRepository.save(bicep4);
        exerciseRepository.save(bicep5);
        exerciseRepository.save(bicep6);

        exerciseRepository.save(benchPress);
        exerciseRepository.save(squat);
        exerciseRepository.save(deadLifts);
    }

}
