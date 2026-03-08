package com.HoopStretchApi.validation.validators;

import com.HoopStretchApi.model.dto.mobilityAssessment.MobilityAssessmentRequestDto;
import com.HoopStretchApi.model.dto.mobilityAssessment.MobilityScoreDto;
import com.HoopStretchApi.util.enums.MobilityArea;
import com.HoopStretchApi.validation.annotations.ValidMobilityAreas;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class MobilityAreaValidator implements ConstraintValidator<ValidMobilityAreas, MobilityAssessmentRequestDto> {

    @Override
    public boolean isValid(final MobilityAssessmentRequestDto mobilityAssessmentRequestDto, final ConstraintValidatorContext context) {
        if (mobilityAssessmentRequestDto == null || mobilityAssessmentRequestDto.getMobilityScores() == null) {
            return false;
        }

        final List<MobilityArea> provided = mobilityAssessmentRequestDto.getMobilityScores().stream()
                .map(MobilityScoreDto::getMobilityArea)
                .toList();

        final Set<MobilityArea> required = EnumSet.allOf(MobilityArea.class);
        final Set<MobilityArea> missing = EnumSet.copyOf(required);

        // Check missing fields
        provided.forEach(missing::remove);
        if (!missing.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Missing mobility areas: " + missing
            ).addConstraintViolation();
            return false;
        }

        // Ensure no duplicates
        if (provided.stream().distinct().count() != provided.size()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Duplicate mobility areas are not allowed"
            ).addConstraintViolation();
            return false;
        }

        return true;
    }
}
