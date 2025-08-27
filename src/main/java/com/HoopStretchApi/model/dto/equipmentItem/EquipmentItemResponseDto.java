package com.HoopStretchApi.model.dto.equipmentItem;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class EquipmentItemResponseDto {
    private Long id;
    private String name;
}
