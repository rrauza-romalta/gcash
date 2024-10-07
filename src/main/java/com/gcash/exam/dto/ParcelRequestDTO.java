package com.gcash.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParcelRequestDTO {
    private double weight; // kg
    private double height; // cm
    private double width;  // cm
    private double length; // cm
    private String voucherCode;

    public double getVolume() {
        return height * width * length;
    }
}
