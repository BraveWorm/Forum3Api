package com.forum.springboot.forum3API.dtos

import dev.krud.shapeshift.enums.AutoMappingStrategy
import dev.krud.shapeshift.resolver.annotation.AutoMapping
import dev.krud.shapeshift.resolver.annotation.DefaultMappingTarget

@AutoMapping(UserDTO::class, strategy = AutoMappingStrategy.BY_NAME)
@AutoMapping(UserRegisterDTO::class, strategy = AutoMappingStrategy.BY_NAME)
@DefaultMappingTarget(UserRegisterDTO::class)
class UserLoginDTO
    (
    val email: String = "",
    val password: String = ""
)