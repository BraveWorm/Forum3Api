package com.forum.springboot.forum3API.repositorys

import com.forum.springboot.forum3API.models.TherapySession
import org.springframework.data.jpa.repository.JpaRepository

interface TherapySessionRepository: JpaRepository<TherapySession, Long> {
}