package com.HoopStretchApi.mapper;

import com.HoopStretchApi.model.dto.mobilityAssessment.MobilityAssessmentResponseDto;
import com.HoopStretchApi.model.dto.mobilityAssessment.MobilityScoreDto;
import com.HoopStretchApi.model.entity.MobilityAssessment;
import com.HoopStretchApi.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MobilityAssessmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assessmentId", source="assessmentId")
    @Mapping(target = "user", source="user")
    MobilityAssessment toMobilityAssessment(final MobilityScoreDto mobilityScoreDto, final Long assessmentId, final User user);

    MobilityScoreDto toMobilityScoreDto(final MobilityAssessment mobilityAssessment);
    List<MobilityScoreDto> toMobilityScoreListDto(final List<MobilityAssessment> mobilityAssessments);

    @Mapping(target = "mobilityScores", source = "mobilityScores")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "assessmentId", source = "assessmentId")
    MobilityAssessmentResponseDto toMobilityAssessmentResponseDto(final List<MobilityScoreDto> mobilityScores, final Long userId, final Long assessmentId);
}
