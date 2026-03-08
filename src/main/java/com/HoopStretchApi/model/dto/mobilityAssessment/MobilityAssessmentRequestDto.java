package com.HoopStretchApi.model.dto.mobilityAssessment;

import com.HoopStretchApi.validation.annotations.ValidMobilityAreas;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ValidMobilityAreas
public class MobilityAssessmentRequestDto {
    private List<MobilityScoreDto> mobilityScores;
}
