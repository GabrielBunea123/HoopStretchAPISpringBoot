package com.HoopStretchApi.model.dto.mobilityAssessment;

import com.HoopStretchApi.util.enums.MobilityArea;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MobilityScoreDto {
    private MobilityArea mobilityArea;
    private int score;
}
