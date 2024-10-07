package com.gcash.exam.config;

import com.gcash.exam.client.VoucherErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new VoucherErrorDecoder();
    }
}
