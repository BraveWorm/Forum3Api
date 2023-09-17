package com.forum.springboot.forum3API.dtos

import com.forum.springboot.forum3API.models.MentalHealthProfessional
import com.forum.springboot.forum3API.services.UserService
import dev.krud.shapeshift.enums.AutoMappingStrategy
import dev.krud.shapeshift.resolver.annotation.AutoMapping

@AutoMapping(MentalHealthProfessional::class, strategy = AutoMappingStrategy.BY_NAME)
class MentalHealthProfessionalDTO(
    var id: Long?,
    val specialization: String?,
    val hourlyRate: Double?,
    var userID: Long?,
) {
    fun mapMentalHealthProfessionalDTOToMentalHealthProfessional( userService: UserService): MentalHealthProfessional {
        return MentalHealthProfessional(
            this.id,
            this.specialization,
            this.hourlyRate,
            userService.getById(this.userID)
        )
    }
}