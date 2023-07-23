package com.forum.springboot.forum3API.services

import com.forum.springboot.forum3API.models.TherapySession
import com.forum.springboot.forum3API.repositorys.TherapySessionRepository
import org.springframework.stereotype.Service

@Service
class TherapySessionService(private val therapySessionRepository: TherapySessionRepository) {

    fun save(therapySession: TherapySession): TherapySession{
        return this.therapySessionRepository.save(therapySession)
    }
}