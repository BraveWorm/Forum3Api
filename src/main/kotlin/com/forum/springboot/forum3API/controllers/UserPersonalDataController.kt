package com.forum.springboot.forum3API.controllers

import com.forum.springboot.forum3API.models.UserPersonalData
import com.forum.springboot.forum3API.services.UserPersonalDataService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api/userpersonaldata")
class UserPersonalDataController (private val userPersonalDataService: UserPersonalDataService) {


    @GetMapping("findbyuserid/{userId}")
    @ResponseStatus(HttpStatus.OK)
    fun test(@PathVariable userId: Long): UserPersonalData? {

        return userPersonalDataService.getByUserId(userId)
    }
}