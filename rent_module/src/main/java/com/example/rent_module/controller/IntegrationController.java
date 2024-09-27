package com.example.rent_module.controller;

import com.example.rent_module.service.IntegrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IntegrationController {

    private final IntegrationService integrationService;

    @GetMapping("/test1")
    public String getIntegrationWithProduct1() {
        return integrationService.integrate1();
    }

    @GetMapping("/test2")
    public String getIntegrationWithProduct2() {
        return integrationService.integrate2();
    }

    @PostMapping("/test3")
    public String getIntegrationWithProduct3() {
        return integrationService.integrate3();
    }
}
