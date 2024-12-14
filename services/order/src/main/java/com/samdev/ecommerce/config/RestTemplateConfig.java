package com.samdev.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(loggingInterceptor()));
        return restTemplate;
    }

    private ClientHttpRequestInterceptor loggingInterceptor() {
        return (request, body, execution) -> {
            System.out.println("Request URI: " + request.getURI());
            System.out.println("Request Method: " + request.getMethod());
            System.out.println("Request Headers: " + request.getHeaders());
            System.out.println("Request Body: " + new String(body));

            ClientHttpResponse response = execution.execute(request, body);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody()));
            StringBuilder responseBody = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                responseBody.append(line);
            }
            System.out.println("Response Body: " + responseBody);
            return response;
        };
    }
}