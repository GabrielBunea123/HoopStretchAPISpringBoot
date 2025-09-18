package com.HoopStretchApi.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "protocol_exercise", schema = "public")
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ProtocolExercise extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "protocol_id", nullable = false)
    private Protocol protocol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Column
    private int duration;

    @Column
    private int orderIndex;

}
