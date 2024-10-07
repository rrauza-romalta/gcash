package com.gcash.exam.client;

import com.gcash.exam.config.FeignConfig;
import com.gcash.exam.dto.VoucherResponseDTO;
import com.gcash.exam.mock.VoucherException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "voucherClient", url = "http://localhost:8087/api/v1/voucher", configuration = FeignConfig.class)
public interface VoucherClient {

    @GetMapping("/vouchers/{code}")
    VoucherResponseDTO getVoucher(@PathVariable("code") String code) throws VoucherException;
}