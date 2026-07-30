package com.HoopStretchApi.util;

import com.HoopStretchApi.util.enums.ExerciseType;
import com.HoopStretchApi.util.enums.ProtocolPurpose;
import lombok.experimental.UtilityClass;

import java.util.Map;

import static com.HoopStretchApi.util.enums.ExerciseType.*;

@UtilityClass
public class Constants {
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String DEFAULT_PAGE = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String ROLE_PREFIX = "ROLE_";
    public static final int MIN_MOBILITY_ASSESSMENT_SCORE = 1;
    public static final int MAX_MOBILITY_ASSESSMENT_SCORE = 10;

    public static final Map<ProtocolPurpose, Map<ExerciseType, Double>> PROTOCOL_EXERCISE_TYPE_RATIOS_BY_PURPOSE =
            Map.of(
                    ProtocolPurpose.DAILY,    Map.of(STATIC, 0.35, DYNAMIC, 0.35, MASSAGE, 0.20),
                    ProtocolPurpose.PREGAME,  Map.of(STATIC, 0.30, DYNAMIC, 0.60, MASSAGE, 0.10),
                    ProtocolPurpose.POSTGAME, Map.of(STATIC, 0.60, MASSAGE, 0.40)
            );
}
