package com.forum.springboot.forum3API.controllers

import com.forum.springboot.forum3API.config.NotionConfigProperties
import com.forum.springboot.forum3API.dtos.ResponseMessage
import com.forum.springboot.forum3API.dtos.UserPersonalDataDTO
import com.forum.springboot.forum3API.models.UserPersonalData
import com.forum.springboot.forum3API.services.UserPersonalDataService
import com.forum.springboot.forum3API.services.UserService
import io.jsonwebtoken.Jwts
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api/userpersonaldata")
class UserPersonalDataController (private val userPersonalDataService: UserPersonalDataService,
                                  private val userService: UserService) {
    val notionConfigProperties = NotionConfigProperties()

    @GetMapping("findbyuserid/{userId}")
    @ResponseStatus(HttpStatus.OK)
    fun test(@PathVariable userId: Long): UserPersonalData? {

        return userPersonalDataService.getByUserId(userId)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun postUserPersonalData(@CookieValue("jwt") jwt: String, @RequestBody body: UserPersonalDataDTO): Any? {
        return try {
//            if(jwt == null)
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseMessage("unauthenticated"))
            val userId = Jwts.parser().setSigningKey(notionConfigProperties.authToken).parseClaimsJws(jwt).body.issuer.toLong()
            body.userID = userId
            val newUserPersonalData = body.mapUserPersonalDataDTOToUserPersonalData(userService)



            this.userPersonalDataService.save(newUserPersonalData)
            "{\"isCreated\": true}"
        } catch (e: Exception) {
            println(e)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseMessage("INTERNAL_SERVER_ERROR"))
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    fun putUserPersonalData(@CookieValue("jwt") jwt: String, @RequestBody body: UserPersonalDataDTO): Any? {
        return try {
            val userId = Jwts.parser().setSigningKey(notionConfigProperties.authToken).parseClaimsJws(jwt).body.issuer.toLong()
            body.userID = userId
            val newUserPersonalData = body.mapUserPersonalDataDTOToUserPersonalData(userService)
            val userPersonalDataId = this.userPersonalDataService.getByUserId(userId)?.id
            newUserPersonalData.id = userPersonalDataId
            this.userPersonalDataService.save(newUserPersonalData)
            "{\"isCreated\": true}"

        } catch (e: Exception) {
            println(e)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseMessage("INTERNAL_SERVER_ERROR"))
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getUserPersonalData(@CookieValue("jwt") jwt: String): Any? {
        return try {
            val userId = Jwts.parser().setSigningKey(notionConfigProperties.authToken).parseClaimsJws(jwt).body.issuer.toLong()
            return  userPersonalDataService.getByUserId(userId)?.mapUserPersonalDataToUserPersonalDataDTO()
        } catch (e: Exception) {
            println(e)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseMessage("INTERNAL_SERVER_ERROR"))
        }
    }
}