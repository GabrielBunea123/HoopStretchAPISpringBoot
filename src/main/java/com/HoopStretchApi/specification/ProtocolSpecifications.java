package com.HoopStretchApi.specification;

import com.HoopStretchApi.model.dto.protocol.ProtocolFilterDto;
import com.HoopStretchApi.model.entity.Protocol;
import com.HoopStretchApi.util.enums.ProtocolVisibility;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ProtocolSpecifications {

    public static Specification<Protocol> hasNameLike(final String name) {
        return (root, query, cb) -> {
            if (name == null || name.trim().isEmpty()) {
                return null;
            }
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Protocol> hasVisibility(final ProtocolVisibility visibility) {
        return (root, query, cb) -> cb.equal(root.get("visibility"), visibility);
    }

    public Specification<Protocol> buildFilters(final ProtocolFilterDto protocolFilterDto) {
        return Specification.allOf(
                ProtocolSpecifications.hasNameLike(protocolFilterDto.getName()),
                ProtocolSpecifications.hasVisibility(protocolFilterDto.getVisibility())
        );
    }
}
