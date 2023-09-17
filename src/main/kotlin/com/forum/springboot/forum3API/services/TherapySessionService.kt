package com.forum.springboot.forum3API.services

import com.forum.springboot.forum3API.models.TherapySession
import com.forum.springboot.forum3API.models.User
import com.forum.springboot.forum3API.repositorys.TherapySessionRepository
import org.springframework.stereotype.Service

@Service
class TherapySessionService(private val therapySessionRepository: TherapySessionRepository) {

    fun save(therapySession: TherapySession): TherapySession{
        return this.therapySessionRepository.save(therapySession)
    }

    fun getAllByUserId(user: User): List<TherapySession>{
        return this.therapySessionRepository.findAllByUserTherapySessions(user)
    }

    fun getById(id: Long?): TherapySession{
        if (id == null) throw IllegalArgumentException("param cannot be null.")
        return this.therapySessionRepository.getById(id)
    }
}