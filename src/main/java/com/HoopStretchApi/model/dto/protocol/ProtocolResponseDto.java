package com.HoopStretchApi.model.dto.protocol;

import com.HoopStretchApi.model.dto.user.UserResponseDto;
import com.HoopStretchApi.util.enums.ProtocolCategory;
import com.HoopStretchApi.util.enums.ProtocolType;
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
    private ProtocolCategory category;
    private ProtocolType type;
    private ProtocolVisibility visibility;
    private int durationSeconds;
    private UserResponseDto owner;
    private Set<ProtocolExerciseResponseDto> exercises = new LinkedHashSet<>();
}
