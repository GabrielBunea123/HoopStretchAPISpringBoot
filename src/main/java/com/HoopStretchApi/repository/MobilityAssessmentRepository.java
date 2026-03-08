package com.HoopStretchApi.repository;

import com.HoopStretchApi.model.entity.MobilityAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MobilityAssessmentRepository extends JpaRepository<MobilityAssessment, Long> {

    @Query("""
        SELECT MAX(ma.assessmentId)
        FROM MobilityAssessment ma
        WHERE ma.user.id = :userId
    """)
    Optional<Long> findMaxAssessmentIdByUserId(final Long userId);

    List<MobilityAssessment> findAllByAssessmentIdAndParentMobilityAreaIsNull(final Long assessmentId);
    List<MobilityAssessment> findAllByUserIdAndAssessmentId(final Long userId, final Long assessmentId);
}
