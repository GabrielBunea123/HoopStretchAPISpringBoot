package com.HoopStretchApi.model.entity;

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

    @ManyToMany(mappedBy = "muscleGroups")
    private Set<Exercise> exercises = new HashSet<>();
}
