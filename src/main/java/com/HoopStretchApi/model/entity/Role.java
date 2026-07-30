package com.HoopStretchApi.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "roles", schema = "public")
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Role extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150, unique = true)
    private String name;

    @Column
    private String description;
}
