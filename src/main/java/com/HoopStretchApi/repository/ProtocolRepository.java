package com.HoopStretchApi.repository;

import com.HoopStretchApi.model.entity.Protocol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProtocolRepository extends JpaRepository<Protocol, Long>, JpaSpecificationExecutor<Protocol> {
    Optional<Protocol> findByIdAndOwnerId(final Long id, final Long ownerId);
}
