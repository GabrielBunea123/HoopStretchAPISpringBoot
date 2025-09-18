package com.HoopStretchApi.model.entity;

import com.HoopStretchApi.util.enums.ProtocolCategory;
import com.HoopStretchApi.util.enums.ProtocolType;
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
    @Column(nullable=false)
    private ProtocolCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProtocolType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProtocolVisibility visibility;

    @Column(nullable = false)
    private int durationSeconds;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;

    @OneToMany(mappedBy = "protocol", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    private Set<ProtocolExercise> exercises = new LinkedHashSet<>();

}
