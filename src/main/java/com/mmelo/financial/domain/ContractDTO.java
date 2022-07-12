package com.mmelo.financial.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractDTO {
    private Long id;
    private String name;
    private LocalDateTime contractStartDate;
    private LocalDateTime contractEndDate;
    private CompanyDTO company;
}