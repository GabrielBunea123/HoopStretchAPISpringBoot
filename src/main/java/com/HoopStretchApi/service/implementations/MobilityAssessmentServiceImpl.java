package com.HoopStretchApi.service.implementations;

import com.HoopStretchApi.exception.NotFoundException;
import com.HoopStretchApi.mapper.MobilityAssessmentMapper;
import com.HoopStretchApi.model.dto.mobilityAssessment.MobilityAssessmentRequestDto;
import com.HoopStretchApi.model.dto.mobilityAssessment.MobilityAssessmentResponseDto;
import com.HoopStretchApi.model.dto.mobilityAssessment.MobilityScoreDto;
import com.HoopStretchApi.model.entity.MobilityAssessment;
import com.HoopStretchApi.model.entity.User;
import com.HoopStretchApi.repository.MobilityAssessmentRepository;
import com.HoopStretchApi.service.MobilityAssessmentService;
import com.HoopStretchApi.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MobilityAssessmentServiceImpl implements MobilityAssessmentService {

    private final MobilityAssessmentRepository mobilityAssessmentRepository;
    private final MobilityAssessmentMapper mobilityAssessmentMapper;
    private final UserService userService;

    @Transactional
    @Override
    public MobilityAssessmentResponseDto createMobilityAssessment(
            final String username,
            final MobilityAssessmentRequestDto mobilityAssessmentRequestDto) {

        final User user = userService.getUserByUsername(username);
        final Optional<Long> prevAssessmentId = mobilityAssessmentRepository.findMaxAssessmentIdByUserId(user.getId());
        if (prevAssessmentId.isEmpty()) {
            log.warn("No prev assessment id found for user {}", username);
        }
        final Long nextAssessmentId = prevAssessmentId.map(assessmentId -> assessmentId + 1).orElse(0L);

        final List<MobilityAssessment> assessment = mobilityAssessmentRequestDto.getMobilityScores()
                .stream()
                .map(scoreDto ->
                        mobilityAssessmentMapper.toMobilityAssessment(
                                scoreDto,
                                nextAssessmentId,
                                user
                        )
                )
                .toList();
        final List<MobilityAssessment> assessmentResults = mobilityAssessmentRepository.saveAll(assessment);
        final List<MobilityScoreDto> mobilityScores = mobilityAssessmentMapper.toMobilityScoreListDto(assessmentResults);
        return mobilityAssessmentMapper.toMobilityAssessmentResponseDto(mobilityScores, user.getId(), nextAssessmentId);
    }

    @Override
    public MobilityAssessmentResponseDto getLatestMobilityAssessment(final String username){

        final User user = userService.getUserByUsername(username);
        final Long latestAssessmentId = mobilityAssessmentRepository.findMaxAssessmentIdByUserId(user.getId())
                .orElseThrow(() -> new NotFoundException("No assessment found for user"));

        final List<MobilityAssessment> assessmentResults = mobilityAssessmentRepository.findAllByUserIdAndAssessmentId(user.getId(), latestAssessmentId);
        if(assessmentResults.isEmpty()){
            log.error("No assessment found for user {}", username);
            throw new NotFoundException("No assessment found for user");
        }

        final List<MobilityScoreDto> mobilityScores = mobilityAssessmentMapper.toMobilityScoreListDto(assessmentResults);
        return mobilityAssessmentMapper.toMobilityAssessmentResponseDto(mobilityScores, user.getId(), latestAssessmentId);
    }

    @Override
    @Transactional
    public MobilityAssessmentResponseDto getAssessmentMainAreasByAssessmentId(final Long assessmentId) {
        final List<MobilityAssessment> assessmentResults = mobilityAssessmentRepository.findAllByAssessmentIdAndParentMobilityAreaIsNull(assessmentId);
        if(assessmentResults.isEmpty()){
            log.error("No assessment found by id {}", assessmentId);
            throw new NotFoundException("No assessment found with assessment id " + assessmentId);
        }
        final User owner = assessmentResults.getFirst().getUser();
        final List<MobilityScoreDto> mobilityScores = mobilityAssessmentMapper.toMobilityScoreListDto(assessmentResults);
        return mobilityAssessmentMapper.toMobilityAssessmentResponseDto(mobilityScores, owner.getId(), assessmentId);
    }
}
