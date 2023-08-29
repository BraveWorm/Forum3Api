package com.forum.springboot.forum3API.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.forum.springboot.forum3API.config.NotionConfigProperties
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import java.util.*
import javax.servlet.http.Cookie

@SpringBootTest
@AutoConfigureMockMvc
internal class TherapySessionControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
){
    val baseUrl = "/api/therapySession"
    // creating cookie/logging in
    fun createJWT(issuer: String): Cookie {
        val notionConfigProperties = NotionConfigProperties()
        val jwt = Jwts.builder()
            .setIssuer(issuer)
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
            .signWith(SignatureAlgorithm.HS256, notionConfigProperties.authToken)
            .compact()
        return Cookie("jwt", jwt);
    }
    
    @Nested
    @DisplayName("POST api/therapySession")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class AddTherapySession {
        @Test
        fun `should create therapySession and return isCreated = true`() {
            // given


            // when

            
            // then

        }
         
    }

}