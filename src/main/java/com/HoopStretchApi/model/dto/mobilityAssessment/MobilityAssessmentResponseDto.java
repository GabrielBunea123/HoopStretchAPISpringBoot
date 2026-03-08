package com.HoopStretchApi.model.dto.mobilityAssessment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class MobilityAssessmentResponseDto {
    private Long userId;
    private Long assessmentId;
    private List<MobilityScoreDto> mobilityScores;
}
