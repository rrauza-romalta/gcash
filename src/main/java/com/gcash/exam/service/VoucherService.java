package com.gcash.exam.service;

import com.gcash.exam.client.VoucherClient;
import com.gcash.exam.dto.VoucherResponseDTO;
import com.gcash.exam.exception.ErrorCode;
import com.gcash.exam.exception.ParcelException;
import com.gcash.exam.mock.VoucherException;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoucherService {

    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);

    private final VoucherClient voucherClient;

    public double getVoucher(String voucherCode) {
        VoucherResponseDTO voucher = null;
        if (StringUtils.isNotEmpty(voucherCode)) {
            try {
                voucher = voucherClient.getVoucher(voucherCode);
            } catch (VoucherException e) {
                logger.warn(e.getMessage());
                throw new ParcelException(ErrorCode.VOUCHER_INVALID);
            } catch (Exception e) {
                logger.warn(e.getMessage());
                throw new ParcelException(ErrorCode.VOUCHER_UNVERIFIED);
            }
        }

        return voucher == null? 0.0d:voucher.getDiscount();
    }
}
