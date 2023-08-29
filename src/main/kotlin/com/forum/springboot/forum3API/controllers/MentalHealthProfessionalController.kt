package com.forum.springboot.forum3API.controllers

import com.forum.springboot.forum3API.config.NotionConfigProperties
import com.forum.springboot.forum3API.dtos.MentalHealthProfessionalDTO
import com.forum.springboot.forum3API.dtos.PatientDTO
import com.forum.springboot.forum3API.dtos.ResponseMessage
import com.forum.springboot.forum3API.services.MentalHealthProfessionalService
import com.forum.springboot.forum3API.services.UserService
import io.jsonwebtoken.Jwts
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api/mentalHealthProfessional")
class MentalHealthProfessionalController(
    private val mentalHealthProfessionalService: MentalHealthProfessionalService,
    private val userService: UserService
        ) {
    val notionConfigProperties = NotionConfigProperties()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun postMentalHealthProfessional (@CookieValue("jwt") jwt: String, @RequestBody body: MentalHealthProfessionalDTO): Any?{
        return try {
            val userId = Jwts.parser().setSigningKey(notionConfigProperties.authToken).parseClaimsJws(jwt).body.issuer.toLong()
            body.userID = userId
            val newMentalHealthProfessional = body.mapMentalHealthProfessionalDTOToMentalHealthProfessional(userService)
            this.mentalHealthProfessionalService.save(newMentalHealthProfessional)
            "{\"isCreated\": true}"

        } catch (e: Exception) {
            println(e)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseMessage("INTERNAL_SERVER_ERROR"))
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    fun putUserPersonalData(@CookieValue("jwt") jwt: String, @RequestBody body: MentalHealthProfessionalDTO): Any? {
        return try {
            val userId = Jwts.parser().setSigningKey(notionConfigProperties.authToken).parseClaimsJws(jwt).body.issuer.toLong()
            body.userID = userId
            val newMentalHealthProfessional = body.mapMentalHealthProfessionalDTOToMentalHealthProfessional(userService)
            val mentalHealthProfessionalId = this.mentalHealthProfessionalService.getByUserId(userId)?.id
            newMentalHealthProfessional.id = mentalHealthProfessionalId
            this.mentalHealthProfessionalService.save(newMentalHealthProfessional)
            "{\"isCreated\": true}"

        } catch (e: Exception) {
            println(e)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseMessage("INTERNAL_SERVER_ERROR"))
        }

    }

}