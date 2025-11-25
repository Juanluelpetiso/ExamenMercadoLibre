package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dna_records", indexes = {
        @Index(name = "idx_dna_hash", columnList = "dnaHash", unique = true)
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DnaRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String dnaHash;

    @Lob
    @Column(nullable = false)
    private String dnaSequence;

    @Column(nullable = false)
    private boolean isMutant;

    @Column(nullable = false, updatable = false)
    private java.time.LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = java.time.LocalDateTime.now();
    }
}
