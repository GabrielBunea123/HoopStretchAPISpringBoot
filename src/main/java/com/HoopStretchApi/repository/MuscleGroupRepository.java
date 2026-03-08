package com.HoopStretchApi.repository;

import com.HoopStretchApi.model.entity.MuscleGroup;
import com.HoopStretchApi.util.enums.MobilityArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MuscleGroupRepository extends JpaRepository<MuscleGroup, Long> {
    List<MuscleGroup> findAllByMobilityArea(final MobilityArea mobilityArea);
}
