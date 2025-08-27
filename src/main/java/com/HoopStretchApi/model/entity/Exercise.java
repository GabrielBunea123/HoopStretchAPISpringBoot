package com.HoopStretchApi.model.entity;

import com.HoopStretchApi.util.enums.ExerciseType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "exercise", schema = "public")
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Exercise extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length = 150)
    private String name;

    @Column(nullable=false)
    private boolean isDoubleSided;

    @ManyToMany
    @JoinTable(
            name = "exercise_muscle_group",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "muscle_group_id")
    )
    private Set<MuscleGroup> muscleGroups = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "exercise_quipment_item",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_item_id")
    )
    private Set<EquipmentItem> equipment = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private ExerciseType type;

    @ElementCollection
    @CollectionTable(
            name = "exercise_guidelines",
            joinColumns = @JoinColumn(name = "exercise_id")
    )
    @Column(name = "guideline")
    private List<String> guidelines = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "exercise_contraindications",
            joinColumns = @JoinColumn(name = "exercise_id")
    )
    @Column(name = "contraindication")
    private List<String> contraindications = new ArrayList<>();

    @Column(nullable = false)
    private boolean isInMobilityTest = false;

    @Column(name = "cover_url")
    private String coverURL;

    @Column(name = "video_url")
    private String videoURL;

}
