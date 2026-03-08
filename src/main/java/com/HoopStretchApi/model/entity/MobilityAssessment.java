package com.HoopStretchApi.model.entity;

import com.HoopStretchApi.util.Constants;
import com.HoopStretchApi.util.enums.MobilityArea;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Table(name="mobility_assessment", schema = "public")
@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class MobilityAssessment extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long assessmentId;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MobilityArea mobilityArea;

    @Enumerated(EnumType.STRING)
    private MobilityArea parentMobilityArea;

    @Min(Constants.MIN_MOBILITY_ASSESSMENT_SCORE)
    @Max(Constants.MAX_MOBILITY_ASSESSMENT_SCORE)
    private int score;
}
