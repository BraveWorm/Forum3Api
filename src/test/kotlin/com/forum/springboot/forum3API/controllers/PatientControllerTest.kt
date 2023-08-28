package com.forum.springboot.forum3API.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.forum.springboot.forum3API.config.NotionConfigProperties
import com.forum.springboot.forum3API.dtos.PatientDTO
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
import org.springframework.test.web.servlet.put
import java.util.*
import javax.servlet.http.Cookie

@SpringBootTest
@AutoConfigureMockMvc
internal class PatientControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
){
    val baseUrl = "/api/patient"
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
    @DisplayName("POST api/patient")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class AddPatient {
        @Test
        fun `should create Patient and return isCreated = true`() {
            // given
            // creating cookie/logging in
            val cookie = createJWT("3")
            cookie.isHttpOnly = true
            cookie.path = "/api/"

            val newPatientDTO = PatientDTO(
                null,
                "healthProblems",
                "therapeuticRequirements",
                3
            )
            val expectedJson = "{\"isCreated\": true}"
            // when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newPatientDTO)
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
        fun `should return INTERNAL_SERVER_ERROR if Patient already exist in DB`() {
            // given
            // creating cookie/logging in
            val cookie = createJWT("1")
            cookie.isHttpOnly = true
            cookie.path = "/api/"

            val newPatientDTO = PatientDTO(
                null,
                "healthProblems",
                "therapeuticRequirements",
                1
            )
            // when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newPatientDTO)
                cookie(cookie)
            }

            // then
            performPost.andDo { print() }
                .andExpect {
                    status { isInternalServerError() }
                }
        }
    }

    @Nested
    @DisplayName("PUT api/patient")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class ChangePatient {
            @Test
            fun `should should change Patient and return status OK`() {
                // given
                // creating cookie/logging in
                val cookie = createJWT("4")
                cookie.isHttpOnly = true
                cookie.path = "/api/"

                // when
                val newPatientDTO = PatientDTO(
                    null,
                    "newHealthProblems",
                    "newTherapeuticRequirements",
                    4
                )

                val performPost = mockMvc.put(baseUrl) {
                    contentType = MediaType.APPLICATION_JSON
                    content = objectMapper.writeValueAsString(newPatientDTO)
                    cookie(cookie)
                }

                // then
                performPost.andDo { print() }
                    .andExpect {
                        status { isOk() }
                    }
            }

        @Test
        fun `should return BAD_REQUEST if there is no Patient in request`() {
            // given
            // creating cookie/logging in
            val cookie = createJWT("4")
            cookie.isHttpOnly = true
            cookie.path = "/api/"

            // when
            val performPost = mockMvc.put(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                cookie(cookie)
            }

            // then
            performPost.andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                }
        }

    }

}