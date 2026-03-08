package com.HoopStretchApi.model.entity;

import com.HoopStretchApi.util.enums.MobilityArea;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Table(name = "muscle_group", schema = "public")
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MuscleGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150, unique = true)
    private String name;

    @Column(nullable = false)
    private int defaultPriorityNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MobilityArea mobilityArea;

    @ManyToMany(mappedBy = "muscleGroups")
    private Set<Exercise> exercises = new HashSet<>();

    @OneToMany(mappedBy = "muscleGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserMuscle> userMuscles;
}
