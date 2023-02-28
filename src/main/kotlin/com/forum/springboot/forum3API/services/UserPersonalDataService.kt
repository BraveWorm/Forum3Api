package com.forum.springboot.forum3API.services

import com.forum.springboot.forum3API.models.UserPersonalData
import com.forum.springboot.forum3API.repositorys.UserPersonalDataRepository
import org.springframework.stereotype.Service

@Service
class UserPersonalDataService (private val userPersonalDataRepository: UserPersonalDataRepository) {
    fun getById(id: Long): UserPersonalData {
        return this.userPersonalDataRepository.getById(id)
    }

    fun getByUserId(id: Long): UserPersonalData? {
        return this.userPersonalDataRepository.findByUserId(id)
    }
}