package com.forum.springboot.forum3API.services

import com.forum.springboot.forum3API.models.MentalHealthProfessional
import com.forum.springboot.forum3API.repositorys.MentalHealthProfessionalRepository
import org.springframework.stereotype.Service

@Service
class MentalHealthProfessionalService(private val mentalHealthProfessionalRepository: MentalHealthProfessionalRepository) {

    fun save(mentalHealthProfessional: MentalHealthProfessional): MentalHealthProfessional {
        return this.mentalHealthProfessionalRepository.save(mentalHealthProfessional)
    }

    fun getByUserId(userId: Long): MentalHealthProfessional? {
        return this.mentalHealthProfessionalRepository.findByUserId(userId)
    }
}