package com.gcash.exam.service;

import com.gcash.exam.dto.ParcelRequestDTO;
import com.gcash.exam.dto.ParcelResponseDTO;
import com.gcash.exam.exception.ErrorCode;
import com.gcash.exam.exception.ParcelException;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParcelService {

    private static final Logger logger = LoggerFactory.getLogger(ParcelService.class);

    public static final String REJECT = "Reject";

    private final KieContainer kieContainer;
    private final VoucherService voucherService;

    public ParcelResponseDTO calculateCost(
            ParcelRequestDTO request) {

        double discount = voucherService.getVoucher(request.getVoucherCode());

        KieSession kieSession = kieContainer.newKieSession();

        // Set the global response object
        ParcelResponseDTO response = new ParcelResponseDTO();
        kieSession.setGlobal("response", response);

        // Insert the request as a fact
        kieSession.insert(request);

        // Fire all rules
        kieSession.fireAllRules();

        // Dispose the session
        kieSession.dispose();

        response.setDiscount(discount);
        if (response.getCost() < discount) {
            response.setTotalCost(0.0);
        } else {
            response.setTotalCost(response.getCost() - discount);
        }
        if (REJECT.equals(response.getRuleApplied())) {
            throw new ParcelException(ErrorCode.REJECTED);
        }

        return response;
    }
}
