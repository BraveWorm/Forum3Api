package com.forum.springboot.forum3API.dtos

import dev.krud.shapeshift.enums.AutoMappingStrategy
import dev.krud.shapeshift.resolver.annotation.AutoMapping

@AutoMapping(UserLoginDTO::class, strategy = AutoMappingStrategy.BY_NAME)
class UserDTO(
    val id: Long? = null,
    val email: String = ""
)
