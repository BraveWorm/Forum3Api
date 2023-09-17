package com.forum.springboot.forum3API.dtos

import com.fasterxml.jackson.annotation.JsonFormat
import com.forum.springboot.forum3API.models.TherapySession
import com.forum.springboot.forum3API.models.User
import com.forum.springboot.forum3API.services.UserService
import dev.krud.shapeshift.enums.AutoMappingStrategy
import dev.krud.shapeshift.resolver.annotation.AutoMapping
import java.time.LocalDateTime

@AutoMapping(TherapySession::class, strategy = AutoMappingStrategy.BY_NAME)
class TherapySessionDTO(
    var id:Long?,
    var status: String?,
    var startOfTherapySession: LocalDateTime?,
    var endOfTherapySession: LocalDateTime?,
    var userTherapySessions: MutableList<Long?> = mutableListOf()
) {
    fun mapTherapySessionDTOToTherapySession(userService: UserService): TherapySession {
        return TherapySession(
            this.id,
            this.status,
            this.startOfTherapySession,
            this.endOfTherapySession,
            userService.getByMutableListOfIds(this.userTherapySessions)
        )
    }
}