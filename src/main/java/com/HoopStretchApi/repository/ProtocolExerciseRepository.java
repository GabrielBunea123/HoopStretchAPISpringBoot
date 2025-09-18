package com.HoopStretchApi.repository;

import com.HoopStretchApi.model.entity.ProtocolExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProtocolExerciseRepository extends JpaRepository <ProtocolExercise, Long>, JpaSpecificationExecutor<ProtocolExercise> {
}
