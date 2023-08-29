package com.forum.springboot.forum3API.controllers

import com.forum.springboot.forum3API.services.TherapySessionService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/therapySession")
class TherapySessionController(
    private val therapySessionService: TherapySessionService
) {
    //TODO PostMapping
    //TODO PutMapping
    //TODO GetMapping
}