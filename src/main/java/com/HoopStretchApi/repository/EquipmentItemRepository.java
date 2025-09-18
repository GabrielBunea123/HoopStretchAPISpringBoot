package com.HoopStretchApi.repository;

import com.HoopStretchApi.model.entity.EquipmentItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentItemRepository extends JpaRepository<EquipmentItem, Long> {
}
