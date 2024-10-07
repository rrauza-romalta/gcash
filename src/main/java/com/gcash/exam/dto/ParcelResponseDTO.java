package com.gcash.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParcelResponseDTO {
    private String ruleApplied;
    private double cost;
    private double discount;
    private double totalCost;
    private String message;
}
