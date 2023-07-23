package com.forum.springboot.forum3API.repositorys

import com.forum.springboot.forum3API.models.MentalHealthProfessional
import org.springframework.data.jpa.repository.JpaRepository

interface MentalHealthProfessionalRepository: JpaRepository<MentalHealthProfessional, Long> {
}