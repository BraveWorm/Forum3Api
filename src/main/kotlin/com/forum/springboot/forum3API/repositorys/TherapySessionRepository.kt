package com.forum.springboot.forum3API.repositorys

import com.forum.springboot.forum3API.models.TherapySession
import com.forum.springboot.forum3API.models.User
import org.springframework.data.jpa.repository.JpaRepository

interface TherapySessionRepository: JpaRepository<TherapySession, Long> {
    fun findAllByUserTherapySessions(user: User): List<TherapySession>
}