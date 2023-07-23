package com.forum.springboot.forum3API.repositorys

import com.forum.springboot.forum3API.models.Patient
import org.springframework.data.jpa.repository.JpaRepository

interface PatientRepository: JpaRepository<Patient, Long> {
}