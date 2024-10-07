package com.gcash.exam.controller;

import com.gcash.exam.config.DroolsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/drools")
@RequiredArgsConstructor
public class DroolsController {

    private final DroolsConfig droolsConfig;

    @PostMapping("/refresh")
    public String refreshRules() {
        try {
            droolsConfig.kieContainer();
            return "Rules refreshed successfully";
        } catch (Exception e) {
            return "Error refreshing rules: " + e.getMessage();
        }
    }
}