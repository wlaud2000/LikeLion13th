package com.project.likelion13th.domain.common.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "healthcheck API", description = "헬스 체크 API")
public class HealthCheckController {

    @GetMapping("/healthcheck")
    public String healthCheck() {
        return "I'm healthy";
    }
}
