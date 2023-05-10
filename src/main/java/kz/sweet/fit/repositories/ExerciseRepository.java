package kz.sweet.fit.repositories;

import kz.sweet.fit.models.entity.ExerciseEntity;
import kz.sweet.fit.models.enums.Muscle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<ExerciseEntity, Long> {
    List<ExerciseEntity> findAllByMainMuscle(Muscle muscle);

    @Query(value = "SELECT *from exercises where main_muscle=?1 order BY RANDOM() LIMIT 3", nativeQuery = true)
    List<ExerciseEntity> findTop3Rand(int muscle);

    @Query(value = "SELECT *from exercises where main_muscle=?1 order BY RANDOM() LIMIT 2", nativeQuery = true)
    List<ExerciseEntity> findTop2Rand(int muscle);
}
