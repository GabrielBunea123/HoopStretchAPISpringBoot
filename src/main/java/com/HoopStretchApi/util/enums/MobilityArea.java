package com.HoopStretchApi.util.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MobilityArea {

    // ROOTS
    SHOULDERS("Shoulders", null),
    HIPS("Hips", null),
    POSTERIOR("Posterior", null),
    ANKLES("Ankles", null),

    // SHOULDERS CHILDREN
    SHOULDER_INTERNAL_ROTATION_LEFT("Shoulder internal rotation left", SHOULDERS),
    SHOULDER_INTERNAL_ROTATION_RIGHT("Shoulder internal rotation right", SHOULDERS),
    SHOULDER_EXTERNAL_ROTATION_LEFT("Shoulder external rotation left", SHOULDERS),
    SHOULDER_EXTERNAL_ROTATION_RIGHT("Shoulder external rotation right", SHOULDERS),
    OVERHEAD_FLEXION_LEFT("Overhead flexion left", SHOULDERS),
    OVERHEAD_FLEXION_RIGHT("Overhead flexion right", SHOULDERS),

    // HIPS CHILDREN
    HIP_INTERNAL_ROTATION_LEFT("Hip internal rotation left", HIPS),
    HIP_INTERNAL_ROTATION_RIGHT("Hip internal rotation right", HIPS),
    HIP_EXTERNAL_ROTATION_LEFT("Hip external rotation left", HIPS),
    HIP_EXTERNAL_ROTATION_RIGHT("Hip external rotation right", HIPS),
    HIP_FLEXION_LEFT("Hip flexion left", HIPS),
    HIP_FLEXION_RIGHT("Hip flexion right", HIPS),

    // POSTERIOR CHILDREN
    POSTERIOR_FLEXIBILITY("Posterior flexibility", POSTERIOR),
    HAMSTRING_FLEXIBILITY("Hamstring flexibility", POSTERIOR),

    // ANKLES CHILDREN
    ANKLE_FLEXIBILITY_LEFT("Ankle flexibility left", ANKLES),
    ANKLE_FLEXIBILITY_RIGHT("Ankle flexibility right", ANKLES),

    //OTHERS
    THORACIC_SPINE("Thoracic spine", null),
    ARMS("Arms", null),
    CORE("Core", null);

    private final String value;
    private final MobilityArea parent;
}
