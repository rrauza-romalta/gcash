package com.gcash.exam.service;

import com.gcash.exam.dto.ParcelRequestDTO;
import com.gcash.exam.dto.ParcelResponseDTO;
import com.gcash.exam.exception.ErrorCode;
import com.gcash.exam.exception.ParcelException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ParcelServiceTest {
    @Autowired
    private ParcelService parcelService;

    @Mock
    private VoucherService voucherService;

    @BeforeEach
    void setUp() {
        when(voucherService.getVoucher("DISCOUNT10")).thenReturn(10.0);
        when(voucherService.getVoucher("INVALID")).thenThrow(new ParcelException(ErrorCode.VOUCHER_INVALID));
        when(voucherService.getVoucher("BADREQUEST")).thenThrow(new ParcelException(ErrorCode.VOUCHER_UNVERIFIED));
    }

    @Test
    void calculateCost_withWeightExceeds50kg_shouldThrowParcelException() {
        ParcelRequestDTO parcel = new ParcelRequestDTO(50.1, 10, 10, 10, null);

        ParcelException exception = assertThrows(ParcelException.class, () -> {
            parcelService.calculateCost(parcel);
        });

        assertEquals(ErrorCode.REJECTED.getCode(), exception.getErrorCode().getCode());
        assertEquals(ErrorCode.REJECTED.getMessage(), exception.getErrorCode().getMessage());
    }

    @Test
    void calculateCost_withInvalidVoucher_shouldThrowParcelException() {
        ParcelRequestDTO parcel = new ParcelRequestDTO(50.1, 10, 10, 10, "INVALID");

        ParcelException exception = assertThrows(ParcelException.class, () -> {
            parcelService.calculateCost(parcel);
        });

        assertEquals(ErrorCode.VOUCHER_INVALID.getCode(), exception.getErrorCode().getCode());
        assertEquals(ErrorCode.VOUCHER_INVALID.getMessage(), exception.getErrorCode().getMessage());
    }

    @Test
    void calculateCost_withWeightExceeds10kg_shouldSetCost() {
        ParcelRequestDTO parcel = new ParcelRequestDTO(50.0, 10, 10, 10, null);

        ParcelResponseDTO response = parcelService.calculateCost(parcel);

        assertEquals(1000, response.getCost());
        assertEquals(1000, response.getTotalCost());
        assertEquals(0.0, response.getDiscount());
        assertEquals("Heavy Parcel", response.getRuleApplied());
    }

    @Test
    void calculateCost_withWeightExceeds10kgAndValidDiscount_shouldSetCost() {
        ParcelRequestDTO parcel = new ParcelRequestDTO(50.0, 10, 10, 10, "DISCOUNT10");

        ParcelResponseDTO response = parcelService.calculateCost(parcel);

        assertEquals(1000, response.getCost());
        assertEquals(990, response.getTotalCost());
        assertEquals(10, response.getDiscount());
        assertEquals("Heavy Parcel", response.getRuleApplied());
    }

    @Test
    void calculateCost_witVolumeLessThan1500_shouldSetCost() {
        ParcelRequestDTO parcel = new ParcelRequestDTO(9.0, 1400, 1, 1, null);

        ParcelResponseDTO response = parcelService.calculateCost(parcel);

        assertEquals(42, response.getCost());
        assertEquals(42, response.getTotalCost());
        assertEquals(0.0, response.getDiscount());
        assertEquals("Small Parcel", response.getRuleApplied());
    }

    @Test
    void calculateCost_witVolumeLessThan1500AndValidDiscount_shouldSetCost() {
        ParcelRequestDTO parcel = new ParcelRequestDTO(9.0, 1400, 1, 1, "DISCOUNT10");

        ParcelResponseDTO response = parcelService.calculateCost(parcel);

        assertEquals(42, response.getCost());
        assertEquals(32, response.getTotalCost());
        assertEquals(10, response.getDiscount());
        assertEquals("Small Parcel", response.getRuleApplied());
    }

    @Test
    void calculateCost_witVolumeLessThan2500_shouldSetCost() {
        ParcelRequestDTO parcel = new ParcelRequestDTO(9.0, 2400, 1, 1, null);

        ParcelResponseDTO response = parcelService.calculateCost(parcel);

        assertEquals(96, response.getCost());
        assertEquals(96, response.getTotalCost());
        assertEquals(0.0, response.getDiscount());
        assertEquals("Medium Parcel", response.getRuleApplied());
    }

    @Test
    void calculateCost_witVolumeLessThan2500AndValidDiscount_shouldSetCost() {
        ParcelRequestDTO parcel = new ParcelRequestDTO(9.0, 2400, 1, 1, "DISCOUNT10");

        ParcelResponseDTO response = parcelService.calculateCost(parcel);

        assertEquals(96, response.getCost());
        assertEquals(86, response.getTotalCost());
        assertEquals(10, response.getDiscount());
        assertEquals("Medium Parcel", response.getRuleApplied());
    }

    @Test
    void calculateCost_witVolumeMoreThan2500_shouldSetCost() {
        ParcelRequestDTO parcel = new ParcelRequestDTO(9.0, 2500, 1, 1, null);

        ParcelResponseDTO response = parcelService.calculateCost(parcel);

        assertEquals(125, response.getCost());
        assertEquals(125, response.getTotalCost());
        assertEquals(0.0, response.getDiscount());
        assertEquals("Large Parcel", response.getRuleApplied());
    }

    @Test
    void calculateCost_witVolumeMoreThan2500AndValidDiscount_shouldSetCost() {
        ParcelRequestDTO parcel = new ParcelRequestDTO(9.0, 2500, 1, 1, "DISCOUNT10");

        ParcelResponseDTO response = parcelService.calculateCost(parcel);

        assertEquals(125, response.getCost());
        assertEquals(115, response.getTotalCost());
        assertEquals(10, response.getDiscount());
        assertEquals("Large Parcel", response.getRuleApplied());
    }
}
