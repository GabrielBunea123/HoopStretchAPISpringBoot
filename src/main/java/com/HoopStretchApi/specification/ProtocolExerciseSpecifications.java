package com.HoopStretchApi.specification;

import com.HoopStretchApi.model.dto.exercise.ExerciseFilterDto;
import com.HoopStretchApi.model.entity.Exercise;
import com.HoopStretchApi.model.entity.ProtocolExercise;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ProtocolExerciseSpecifications {

    public static Specification<ProtocolExercise> hasExerciseFilters(final ExerciseFilterDto exerciseFilterDto) {
        return (root, query, cb) -> {
            if(exerciseFilterDto == null){
                return null;
            }
            final Join<ProtocolExercise, Exercise> exerciseJoin = root.join("exercise");

            Predicate predicate = cb.conjunction();

            if (exerciseFilterDto.getName() != null && !exerciseFilterDto.getName().isBlank()) {
                predicate = cb.and(predicate,
                        cb.like(cb.lower(exerciseJoin.get("name")), "%" + exerciseFilterDto.getName().toLowerCase() + "%"));
            }

            if (exerciseFilterDto.getMuscleGroup() != null && !exerciseFilterDto.getMuscleGroup().isBlank()) {
                predicate = cb.and(predicate,
                        cb.equal(cb.lower(exerciseJoin.join("muscleGroups").get("name")), exerciseFilterDto.getMuscleGroup().toLowerCase()));
            }

            if (exerciseFilterDto.getEquipmentItem() != null && !exerciseFilterDto.getEquipmentItem().isBlank()) {
                predicate = cb.and(predicate,
                        cb.equal(cb.lower(exerciseJoin.join("equipment").get("name")), exerciseFilterDto.getEquipmentItem().toLowerCase()));
            }

            if (exerciseFilterDto.getType() != null) {
                predicate = cb.and(predicate,
                        cb.equal(exerciseJoin.get("type"), exerciseFilterDto.getType()));
            }

            return predicate;
        };
    }

    public static Specification<ProtocolExercise> hasProtocolId(Long protocolId) {
        return (root, query, cb) -> cb.equal(root.get("protocol").get("id"), protocolId);
    }

    public Specification<ProtocolExercise> buildFilters(final Long protocolId, final ExerciseFilterDto exerciseFilterDto) {
        return Specification.allOf(
                hasProtocolId(protocolId),
                hasExerciseFilters(exerciseFilterDto)
        );
    }
}
