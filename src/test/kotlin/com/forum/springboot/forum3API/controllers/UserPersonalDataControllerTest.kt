package com.forum.springboot.forum3API.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.forum.springboot.forum3API.config.NotionConfigProperties
import com.forum.springboot.forum3API.dtos.UserPersonalDataDTO
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
import org.springframework.test.web.servlet.post
import java.time.LocalDate
import java.util.*
import javax.servlet.http.Cookie

@SpringBootTest
@AutoConfigureMockMvc
internal class UserPersonalDataControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
){
    val baseUrl = "/api/userpersonaldata"

    @Nested
    @DisplayName("POST api/userpersonaldata")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class  AddUserPersonalData {

        @Test
        fun `should create UserPersonalData and return isCreated = true`() {
            // given
            // creating cookie/logging in
            val notionConfigProperties = NotionConfigProperties()
            val issuer = "1" // userId
            val jwt = Jwts.builder()
                .setIssuer(issuer)
                .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(SignatureAlgorithm.HS256, notionConfigProperties.authToken)
                .compact()
            val cookie = Cookie("jwt", jwt)
            cookie.isHttpOnly = true
            cookie.path = "/api/"

            val newUserPersonalDataDTO = UserPersonalDataDTO(
                null,
                "firstNameTest",
                null,
                "lastNameTest",
                "987456321",
                LocalDate.of(1993, 10, 13),
                "genderTest",
                1
            )
            val expectedJson = "{\"isCreated\": true}"

            // when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newUserPersonalDataDTO)
                cookie(cookie)
            }

            // then
            performPost.andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content { json(expectedJson) }
                }
        }


        @Test
        fun `should return Bad Request if there is no cookie`() {
            // given

            val newUserPersonalDataDTO = UserPersonalDataDTO(
                null,
                "firstNameTest",
                null,
                "lastNameTest",
                "987456321",
                LocalDate.of(1993, 10, 13),
                "genderTest",
                1
            )
            // when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newUserPersonalDataDTO)
            }

            // then
            performPost.andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                }
        }

    }
}