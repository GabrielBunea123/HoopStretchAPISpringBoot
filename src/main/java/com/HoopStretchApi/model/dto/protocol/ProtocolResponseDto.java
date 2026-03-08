package com.HoopStretchApi.model.dto.protocol;

import com.HoopStretchApi.model.dto.user.UserResponseDto;
import com.HoopStretchApi.util.enums.ProtocolTarget;
import com.HoopStretchApi.util.enums.ProtocolPurpose;
import com.HoopStretchApi.util.enums.ProtocolVisibility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProtocolResponseDto {
    private Long id;
    private String name;
    private ProtocolTarget target;
    private ProtocolPurpose purpose;
    private ProtocolVisibility visibility;
    private int durationSeconds;
    private boolean generated;
    private UserResponseDto owner;
    private Set<ProtocolExerciseResponseDto> exercises = new LinkedHashSet<>();
}
