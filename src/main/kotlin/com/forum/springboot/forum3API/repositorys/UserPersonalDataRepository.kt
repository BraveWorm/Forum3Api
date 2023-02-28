package com.forum.springboot.forum3API.repositorys

import com.forum.springboot.forum3API.models.UserPersonalData
import org.springframework.data.jpa.repository.JpaRepository

interface UserPersonalDataRepository: JpaRepository<UserPersonalData, Long> {
    fun findByUserId(user: Long): UserPersonalData?
}