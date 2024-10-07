package com.gcash.exam.controller;

import com.gcash.exam.dto.ParcelRequestDTO;
import com.gcash.exam.dto.ParcelResponseDTO;
import com.gcash.exam.dto.ResponseDTO;
import com.gcash.exam.service.ParcelService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/parcels")
@RequiredArgsConstructor
public class ParcelController {

    private static final Logger logger = LoggerFactory.getLogger(ParcelController.class);

    private final ParcelService parcelService;

    @PostMapping("/calculate")
    public ResponseEntity<ResponseDTO<ParcelResponseDTO>> calculateDeliveryCost(
            @RequestBody ParcelRequestDTO request) {
        ParcelResponseDTO response = parcelService.calculateCost(request);
        return ResponseEntity.ok(new ResponseDTO<>(0, Strings.EMPTY, response, true));
    }
}
