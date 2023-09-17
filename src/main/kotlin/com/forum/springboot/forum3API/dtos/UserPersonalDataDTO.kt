package com.forum.springboot.forum3API.dtos

import com.fasterxml.jackson.annotation.JsonFormat
import com.forum.springboot.forum3API.models.UserPersonalData
import com.forum.springboot.forum3API.services.UserService
import dev.krud.shapeshift.enums.AutoMappingStrategy
import dev.krud.shapeshift.resolver.annotation.AutoMapping
import java.time.LocalDate

@AutoMapping(UserPersonalData::class, strategy = AutoMappingStrategy.BY_NAME)
class UserPersonalDataDTO (
    var id: Long?,
    var firstName: String,
    var secondName: String?,
    var lastName: String?,
    var phoneNumber: String?,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") var dateOfBirth: LocalDate?,
    var gender: String,
    var userID: Long?,
) {
    fun mapUserPersonalDataDTOToUserPersonalData( userService: UserService): UserPersonalData {
        return UserPersonalData(
            this.id,
            this.firstName,
            this.secondName,
            this.lastName,
            this.phoneNumber,
            this.dateOfBirth,
            this.gender,
            userService.getById(this.userID)
        )
    }
}