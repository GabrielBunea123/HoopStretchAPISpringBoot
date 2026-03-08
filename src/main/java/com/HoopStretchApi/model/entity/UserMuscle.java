package com.HoopStretchApi.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "public")
@Getter
@Setter
@RequiredArgsConstructor
public class UserMuscle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private MuscleGroup muscleGroup;

    private int priority;
}
