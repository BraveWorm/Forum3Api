package com.forum.springboot.forum3API.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.forum.springboot.forum3API.config.NotionConfigProperties
import com.forum.springboot.forum3API.dtos.MentalHealthProfessionalDTO
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
import org.springframework.test.web.servlet.put
import java.util.*
import javax.servlet.http.Cookie

@SpringBootTest
@AutoConfigureMockMvc
internal class MentalHealthProfessionalControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {
    val baseUrl = "/api/mentalHealthProfessional"
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
    @DisplayName("POST api/mentalHealthProfessional")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class AddMentalHealthProfessional {    
         @Test
         fun `should create MentalHealthProfessional and return isCreated = true`() {
             // given
             // creating cookie/logging in
             val cookie = createJWT("3")
             cookie.isHttpOnly = true
             cookie.path = "/api/"

             val newMentalHealthProfessionalDTO = MentalHealthProfessionalDTO(
                 null,
                 "specialization",
                 100.00,
                 3
             )
             val expectedJson = "{\"isCreated\": true}"

             // when
             val performPost = mockMvc.post(baseUrl) {
                 contentType = MediaType.APPLICATION_JSON
                 content = objectMapper.writeValueAsString(newMentalHealthProfessionalDTO)
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
        fun `should return INTERNAL_SERVER_ERROR if MentalHealthProfessional already exist in DB`() {
            // given
            // creating cookie/logging in
            val cookie = createJWT("4")
            cookie.isHttpOnly = true
            cookie.path = "/api/"

            val newMentalHealthProfessionalDTO = MentalHealthProfessionalDTO(
                null,
                "specialization",
                250.00,
                4
            )

            // when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newMentalHealthProfessionalDTO)
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
    @DisplayName("PUT api/mentalHealthProfessional")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class ChangeMentalHealthProfessional {
    
         @Test
         fun `should change MentalHealthProfessional and return status OK`() {
             // given
             // creating cookie/logging in
             val cookie = createJWT("4")
             cookie.isHttpOnly = true
             cookie.path = "/api/"

             val newMentalHealthProfessionalDTO = MentalHealthProfessionalDTO(
                 null,
                 "newSpecialization",
                 350.00,
                 4
             )
             val expectedJson = "{\"isCreated\": true}"

             // when
             val performPut = mockMvc.put(baseUrl) {
                 contentType = MediaType.APPLICATION_JSON
                 content = objectMapper.writeValueAsString(newMentalHealthProfessionalDTO)
                 cookie(cookie)
             }
             
             // then
             performPut.andDo { print() }
                 .andExpect {
                     status { isOk() }
                     content { json(expectedJson) }
                 }
         }

        @Test
        fun `should return BAD_REQUEST if there is no MentalHealthProfessional in request`() {
            // given
            // creating cookie/logging in
            val cookie = createJWT("4")
            cookie.isHttpOnly = true
            cookie.path = "/api/"

            // when
            val performPut = mockMvc.put(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                cookie(cookie)
            }

            // then
            performPut.andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                }
        }
    }
    
    @Nested
    @DisplayName("GET api/mentalHealthProfessional")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetMentalHealthProfessional {
        @Test
        fun `should get MentalHealthProfessional and return status OK`() {
            // given
            // creating cookie/logging in
            val cookie = createJWT("4")
            cookie.isHttpOnly = true
            cookie.path = "/api/"
            
            // when
            val performGet = mockMvc.get(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                cookie(cookie)
            }
            
            // then
            performGet.andDo { print() }
                .andExpect {
                    status { isOk() }
                }

        }

        @Test
        fun `should return BAD_REQUEST if there is no Cookie in request`() {
            // given


            // when
            val performPost = mockMvc.get(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
            }

            // then
            performPost.andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                }
        }
    }

    @Nested
    @DisplayName("GET api/mentalHealthProfessional/{userId}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetMentalHealthProfessionalByUserId {
        @Test
        fun `should should get MentalHealthProfessional by userId and return status OK`() {
            // given
            val userId="4"

            // when
            val performGet = mockMvc.get("$baseUrl/$userId") {
                contentType = MediaType.APPLICATION_JSON
            }

            // then
            performGet.andDo { print() }
                .andExpect {
                    status { isOk() }
                }
        }

        @Test
        fun `should should return BadRequest if there userId = null`() {
            // given
            val userId=null

            // when
            val performGet = mockMvc.get("$baseUrl/$userId") {
                contentType = MediaType.APPLICATION_JSON
            }

            // then
            performGet.andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                }
        }

    }


}