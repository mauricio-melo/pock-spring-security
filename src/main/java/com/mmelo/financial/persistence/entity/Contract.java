package com.mmelo.financial.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "contract")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idt_contract")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idt_company",nullable = false)
    private Company company;

    @Column(name = "dat_start_contract", nullable = false)
    private LocalDateTime contractStartDate;

    @Column(name = "dat_end_contract", nullable = false)
    private LocalDateTime contractEndDate;

    @Builder.Default
    @Column(name = "is_current", nullable = false)
    private boolean isCurrent = true;

    @CreatedDate
    @Column(name = "dat_creation", updatable = false, nullable = false)
    private LocalDateTime creationDate;

    @LastModifiedDate
    @Column(name = "dat_update", nullable = false)
    private LocalDateTime updateDate;
}