package com.forum.springboot.forum3API.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.forum.springboot.forum3API.config.NotionConfigProperties
import com.forum.springboot.forum3API.dataloader.db.ANSI_CYAN
import com.forum.springboot.forum3API.dataloader.db.ANSI_RESET
import com.forum.springboot.forum3API.dtos.ResponseMessage
import com.forum.springboot.forum3API.dtos.TherapySessionDTO
import com.forum.springboot.forum3API.models.TherapySession
import com.forum.springboot.forum3API.services.TherapySessionService
import com.forum.springboot.forum3API.services.UserService
import com.google.gson.Gson
import dev.krud.shapeshift.dsl.mapper
import io.jsonwebtoken.Jwts
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime

@RestController
@RequestMapping("api/therapySession")
class TherapySessionController(
    private val therapySessionService: TherapySessionService,
    private val userService: UserService
) {
    val notionConfigProperties = NotionConfigProperties()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun postTherapySession(@CookieValue("jwt") jwt: String, @RequestBody body: TherapySessionDTO): Any?{
        return try {
            val userId = Jwts.parser().setSigningKey(notionConfigProperties.authToken).parseClaimsJws(jwt).body.issuer.toLong()
            if (!body.userTherapySessions.contains(userId))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"Wrong Users\"}")
            this.therapySessionService.save(body.mapTherapySessionDTOToTherapySession(userService))
            return "{\"isCreated\": true}"

        } catch (e: Exception) {
            println(e)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseMessage("INTERNAL_SERVER_ERROR"))
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    fun putTherapySession(@CookieValue("jwt") jwt: String, @RequestBody body: TherapySessionDTO): Any? {
        return try {
            val userId =
                Jwts.parser().setSigningKey(notionConfigProperties.authToken).parseClaimsJws(jwt).body.issuer.toLong()

            if (!body.userTherapySessions.contains(userId))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"Wrong Users\"}")
            this.therapySessionService.save(body.mapTherapySessionDTOToTherapySession(userService))
            return "{\"isChanged\": true}"

        } catch (e: Exception) {
            println(e)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseMessage("INTERNAL_SERVER_ERROR"))
        }
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getTherapySession(@CookieValue("jwt") jwt: String): Any? {
        return try {
            val userId = Jwts.parser().setSigningKey(notionConfigProperties.authToken).parseClaimsJws(jwt).body.issuer.toLong()
            return therapySessionService.getAllByUserId(userService.getById(userId))
        } catch (e: Exception) {
            println(e)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseMessage("INTERNAL_SERVER_ERROR"))
        }
    }
}