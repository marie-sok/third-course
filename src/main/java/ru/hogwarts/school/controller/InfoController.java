package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Informational controller", description = "API to get information about the application")
public class InfoController {

    @Value("${server.port}")
    private int serverPort;

    @GetMapping("/port")
    @Operation(summary = "Get the application code", description = "Return port, on which the application running")
    public int getPort() {
        return serverPort;
    }
}