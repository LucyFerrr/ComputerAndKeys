package org.ksa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity for computer record in the database.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "computers", uniqueConstraints = @UniqueConstraint(name = "uk_maker_model", columnNames = {"maker", "model"}),
        indexes = @Index(name = "idx_maker_model", columnList = "maker ,model"))
public class Computer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "maker", nullable = false)
    private String maker;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "language")
    private String language;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "computer_colors", joinColumns = @JoinColumn(name = "computer_id"))
    @Column(name = "color")
    @Builder.Default
    private List<String> colors = new ArrayList<>();
}
