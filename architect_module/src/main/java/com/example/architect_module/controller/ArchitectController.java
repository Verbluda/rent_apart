package com.example.architect_module.controller;

import com.example.architect_module.model.ArchitectRequestDto;
import com.example.architect_module.service.ArchitectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rent_apartment")
public class ArchitectController {

    private final ArchitectService architectService;

    @PostMapping("/architect/automation")
    public void createDBMigration(@RequestBody ArchitectRequestDto architectRequestDto) {
        architectService.createMigrationFile(architectRequestDto);
    }

}
