package com.forum.springboot.forum3API.dtos

import com.forum.springboot.forum3API.models.User
import dev.krud.shapeshift.enums.AutoMappingStrategy
import dev.krud.shapeshift.resolver.annotation.AutoMapping
import dev.krud.shapeshift.resolver.annotation.DefaultMappingTarget
import org.jetbrains.annotations.NotNull
import javax.validation.constraints.Email

@AutoMapping(User::class, strategy = AutoMappingStrategy.BY_NAME)
@AutoMapping(UserDTO::class, strategy = AutoMappingStrategy.BY_NAME)
@AutoMapping(UserLoginDTO::class, strategy = AutoMappingStrategy.BY_NAME)
@DefaultMappingTarget(UserLoginDTO::class)
class UserRegisterDTO (
    val email: String,
    val password: String
        )