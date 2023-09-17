package com.forum.springboot.forum3API.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.forum.springboot.forum3API.config.NotionConfigProperties
import com.forum.springboot.forum3API.dtos.UserLoginDTO
import com.forum.springboot.forum3API.dtos.UserRegisterDTO
import com.forum.springboot.forum3API.models.User
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
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.util.*
import javax.servlet.http.Cookie


@SpringBootTest
@AutoConfigureMockMvc
internal class UserControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
){

    val baseUrl = "/api/users"

    @Nested
    @DisplayName("GET /api/users/whoami")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class WhoAmI {

        @Test
        fun `should return all logged user`() {
            // given
            val notionConfigProperties = NotionConfigProperties()

            val issuer = "1"

            val jwt = Jwts.builder()
                .setIssuer(issuer)
                .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 *24))
                .signWith(SignatureAlgorithm.HS256, notionConfigProperties.authToken)
                .compact()

            val cookie = Cookie("jwt", jwt)
            cookie.isHttpOnly = true
            cookie.path = "/api/"

            // when
            val performGet = mockMvc.get("$baseUrl/whoami") {
                contentType = MediaType.APPLICATION_JSON
                cookie(cookie)
            }

            // then
            performGet.andDo { print() }
                .andExpect {
                    status { isOk() }
                }

        }
    }

}