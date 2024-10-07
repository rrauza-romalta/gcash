package com.gcash.exam.mock;

import com.gcash.exam.dto.VoucherResponseDTO;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/voucher")
@RequiredArgsConstructor
public class VoucherController {
    @GetMapping("/vouchers/{code}")
    public ResponseEntity<?> getVoucherDiscount(@PathVariable("code") String code) {
        VoucherResponseDTO responseDTO = new VoucherResponseDTO();
        if ("DISCOUNT10".equals(code)) {
            responseDTO.setCode("DISCOUNT10");
            responseDTO.setDiscount(10.0);
            responseDTO.setExpiry("2024-12-17");
            return ResponseEntity.ok(responseDTO);
        } else if ("DISCOUNT50".equals(code)) {
            responseDTO.setCode("DISCOUNT50");
            responseDTO.setDiscount(50.0);
            responseDTO.setExpiry("2024-12-17");
            return ResponseEntity.ok(responseDTO);
        } else {
            VoucherErrorDTO errorDTO = new VoucherErrorDTO();
            errorDTO.setError("invalild");
            return ResponseEntity.status(400).body(errorDTO);
        }
    }

}
