package com.HoopStretchApi.model.entity;

import com.HoopStretchApi.util.enums.ProtocolTarget;
import com.HoopStretchApi.util.enums.ProtocolPurpose;
import com.HoopStretchApi.util.enums.ProtocolVisibility;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Table(name = "protocol", schema = "public")
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Protocol extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column
    private ProtocolTarget target;

    @Enumerated(EnumType.STRING)
    @Column
    private ProtocolPurpose purpose;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProtocolVisibility visibility;

    @Column
    private int durationSeconds;

    @Column(nullable = false)
    private boolean generated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;

    @OneToMany(mappedBy = "protocol", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    private Set<ProtocolExercise> exercises = new LinkedHashSet<>();

}
