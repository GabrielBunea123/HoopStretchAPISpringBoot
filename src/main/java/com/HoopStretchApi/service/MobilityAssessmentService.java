package com.HoopStretchApi.service;

import com.HoopStretchApi.model.dto.mobilityAssessment.MobilityAssessmentRequestDto;
import com.HoopStretchApi.model.dto.mobilityAssessment.MobilityAssessmentResponseDto;

public interface MobilityAssessmentService {
    MobilityAssessmentResponseDto createMobilityAssessment(final String username, final MobilityAssessmentRequestDto mobilityAssessmentRequestDto);
    MobilityAssessmentResponseDto getLatestMobilityAssessment(final String username);
    MobilityAssessmentResponseDto getAssessmentMainAreasByAssessmentId(final Long assessmentId);
}
