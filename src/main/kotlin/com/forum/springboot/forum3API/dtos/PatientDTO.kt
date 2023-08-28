package com.forum.springboot.forum3API.dtos

import com.forum.springboot.forum3API.models.Patient
import com.forum.springboot.forum3API.services.UserService
import dev.krud.shapeshift.enums.AutoMappingStrategy
import dev.krud.shapeshift.resolver.annotation.AutoMapping


@AutoMapping(Patient::class, strategy = AutoMappingStrategy.BY_NAME)
class PatientDTO (
    var id: Long?,
    val healthProblems: String?,
    val therapeuticRequirements: String?,
    var userID: Long,
) {
    fun mapPatientDTOToPatient( userService: UserService ): Patient {
        return Patient(
            this.id,
            this.healthProblems,
            this.therapeuticRequirements,
            userService.getById(this.userID)
        )
    }
}
