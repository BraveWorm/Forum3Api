package com.forum.springboot.forum3API.controllers

import com.forum.springboot.forum3API.config.NotionConfigProperties
import com.forum.springboot.forum3API.dtos.PatientDTO
import com.forum.springboot.forum3API.dtos.ResponseMessage
import com.forum.springboot.forum3API.dtos.UserPersonalDataDTO
import com.forum.springboot.forum3API.models.Patient
import com.forum.springboot.forum3API.services.PatientService
import com.forum.springboot.forum3API.services.UserService
import io.jsonwebtoken.Jwts
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/patient")
class PatientController (private val patientService: PatientService,
                         private val userService: UserService) {
    val notionConfigProperties = NotionConfigProperties()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun postPatient (@CookieValue("jwt") jwt: String, @RequestBody body: PatientDTO): Any?{
        return try {
            val userId = Jwts.parser().setSigningKey(notionConfigProperties.authToken).parseClaimsJws(jwt).body.issuer.toLong()
            body.userID = userId
            val newUserPatient = body.mapPatientDTOToPatient(userService)
            this.patientService.save(newUserPatient)
            "{\"isCreated\": true}"

        } catch (e: Exception) {
            println(e)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseMessage("INTERNAL_SERVER_ERROR"))
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    fun putPatient(@CookieValue("jwt") jwt: String, @RequestBody body: PatientDTO): Any? {
        return try {
            val userId = Jwts.parser().setSigningKey(notionConfigProperties.authToken).parseClaimsJws(jwt).body.issuer.toLong()
            body.userID = userId
            val newPatient = body.mapPatientDTOToPatient(userService)
            val patientId = this.patientService.getByUserId(userId)?.id
            newPatient.id = patientId
            this.patientService.save(newPatient)
            "{\"isCreated\": true}"

        } catch (e: Exception) {
            println(e)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseMessage("INTERNAL_SERVER_ERROR"))
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getPatient(@CookieValue("jwt") jwt: String): Any? {
        return try {
            val userId = Jwts.parser().setSigningKey(notionConfigProperties.authToken).parseClaimsJws(jwt).body.issuer.toLong()
            return patientService.getByUserId(userId)?.mapPatientToPatientDTO()


        } catch (e: Exception) {
            println(e)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseMessage("INTERNAL_SERVER_ERROR"))
        }
    }
//    @GetMapping("/{patient}")
//    @ResponseStatus(HttpStatus.OK)
//    fun getPatientById(@CookieValue("jwt") jwt: String, @PathVariable patient: Long): Any? {
//        return try {
//            val userId = Jwts.parser().setSigningKey(notionConfigProperties.authToken).parseClaimsJws(jwt).body.issuer.toLong()
//
//            "{\"isCreated\": true}"
//
//        } catch (e: Exception) {
//            println(e)
//            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseMessage("INTERNAL_SERVER_ERROR"))
//        }
//    }


}