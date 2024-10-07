package com.gcash.exam.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcash.exam.mock.VoucherErrorDTO;
import com.gcash.exam.mock.VoucherException;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;

public class VoucherErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() != 200) {
            // Handle non-200 responses as errors
            ObjectMapper mapper = new ObjectMapper();
            try {
                // Map error response to ErrorResponse class
                VoucherErrorDTO errorResponse = mapper.readValue(response.body().asInputStream(), VoucherErrorDTO.class);
                return new VoucherException(errorResponse.getError());
            } catch (IOException e) {
                return new RuntimeException("Failed to process error response");
            }
        }
        return defaultErrorDecoder.decode(methodKey, response);
    }
}
