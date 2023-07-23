package com.forum.springboot.forum3API.services

import com.forum.springboot.forum3API.models.Patient
import com.forum.springboot.forum3API.repositorys.PatientRepository
import org.springframework.stereotype.Service

@Service
class PatientService(private val patientRepository: PatientRepository) {

    fun save(patient: Patient): Patient{
        return this.patientRepository.save(patient)
    }
}