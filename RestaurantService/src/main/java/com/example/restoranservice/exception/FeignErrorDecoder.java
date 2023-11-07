package com.example.restoranservice.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    // s = feign class name + method name which was called
    public Exception decode(String s, Response response) {
        switch (response.status()){
            case 400:
                // TODO:
                System.out.println("FEIGN DECODER: 400");
                if (s.contains("getProductById")) {
                    return new ResponseStatusException(HttpStatus.valueOf(response.status()), "Product are not found");
                }
                break;
            case 404:
                // TODO:
                break;
            default:
                return new Exception(response.reason());
        }
        return null;
    }
}
