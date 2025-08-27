package com.HoopStretchApi.specification;

import com.HoopStretchApi.model.dto.exercise.ExerciseFilterDto;
import com.HoopStretchApi.model.entity.Exercise;
import com.HoopStretchApi.util.enums.ExerciseType;
import org.springframework.data.jpa.domain.Specification;

public class ExerciseSpecifications {

    public static Specification<Exercise> hasNameLike(final String name) {
        return (root, query, cb) -> {
            if (name == null || name.trim().isEmpty()) {
                return null;
            }
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Exercise> hasMuscleGroup(final String muscleGroupName) {
        return (root, query, cb) -> {
            if(muscleGroupName == null || muscleGroupName.trim().isEmpty()){
                return null;
            }
            return cb.equal(root.join("muscleGroups").get("name"), muscleGroupName);
        };
    }

    public static Specification<Exercise> hasEquipment(final String equipmentItemName) {
        return (root, query, cb) -> {
            if(equipmentItemName == null || equipmentItemName.trim().isEmpty()){
                return null;
            }
            return cb.equal(root.join("equipment").get("name"),  equipmentItemName);
        };
    }

    public static Specification<Exercise> hasType(final ExerciseType exerciseType) {
        return(root, query, cb)->{
            if(exerciseType == null){
                return null;
            }
            return cb.equal(root.get("exerciseType"), exerciseType);
        };
    }
    public Specification<Exercise> buildFilters(final ExerciseFilterDto exerciseFilterDto) {
        return Specification.allOf(
                ExerciseSpecifications.hasNameLike(exerciseFilterDto.getName()),
                ExerciseSpecifications.hasMuscleGroup(exerciseFilterDto.getMuscleGroup()),
                ExerciseSpecifications.hasEquipment(exerciseFilterDto.getEquipmentItem()),
                ExerciseSpecifications.hasType(exerciseFilterDto.getType())
        );
    }
}
