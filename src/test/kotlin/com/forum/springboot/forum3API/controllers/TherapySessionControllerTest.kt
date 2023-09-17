package com.forum.springboot.forum3API.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.forum.springboot.forum3API.config.NotionConfigProperties
import com.forum.springboot.forum3API.dtos.TherapySessionDTO
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
import java.time.LocalDateTime
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
            val cookie = createJWT("1")
            cookie.isHttpOnly = true
            cookie.path = "/api/"

            val newTherapySessionDTO = TherapySessionDTO(
                    null,
                    "testStatus",
                    LocalDateTime.of(2023,10,12,14,30,0),
                    LocalDateTime.of(2023,10,12,15,30,0),
                    mutableListOf(4,1)
                )
            val expectedJson = "{\"isCreated\": true}"

            // when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newTherapySessionDTO)
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
        fun `should return BAD_REQUEST for wrong data`() {
            // given
            val cookie = createJWT("1")
            cookie.isHttpOnly = true
            cookie.path = "/api/"

            val newTherapySessionDTO = TherapySessionDTO(
                null,
                "testStatus",
                LocalDateTime.of(2023,10,12,14,30,0),
                LocalDateTime.of(2023,10,12,15,30,0),
                mutableListOf(99999,99998) // non-existing users
            )
            val expectedJson = "{\"error\": \"Wrong Users\"}"

            // when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newTherapySessionDTO)
                cookie(cookie)
            }

            // then
            performPost.andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                    content { json(expectedJson) }
                }
        }

        @Test
        fun `should return BAD_REQUEST if usersId don't mach with id in therapySessionDTO`() {
            // given
            val cookie = createJWT("1")
            cookie.isHttpOnly = true
            cookie.path = "/api/"

            val newTherapySessionDTO = TherapySessionDTO(
                null,
                "testStatus",
                LocalDateTime.of(2023,10,12,14,30,0),
                LocalDateTime.of(2023,10,12,15,30,0),
                mutableListOf(2,3) // non-existing users
            )
            val expectedJson = "{\"error\": \"Wrong Users\"}"

            // when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newTherapySessionDTO)
                cookie(cookie)
            }

            // then
            performPost.andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                    content { json(expectedJson) }
                }
        }
    }

    @Nested
    @DisplayName("PUT api/therapySession")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PutTherapySession {

         @Test
         fun `should PUT therapy session and return  isCreated = true`() {
             // given
             val cookie = createJWT("1")
             cookie.isHttpOnly = true
             cookie.path = "/api/"

             val newTherapySessionDTO = TherapySessionDTO(
                 2,
                 "testStatus",
                 LocalDateTime.of(2023,10,12,20,0,0),
                 LocalDateTime.of(2023,10,12,21,0,0),
                 mutableListOf(1,2,3)
             )
             val expectedJson = "{\"isChanged\": true}"

             // when
             val performPut = mockMvc.put(baseUrl) {
                 contentType = MediaType.APPLICATION_JSON
                 content = objectMapper.writeValueAsString(newTherapySessionDTO)
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
        fun `should return BAD_REQUEST if usersId don't mach with id in therapySessionDTO`() {
            // given
            val cookie = createJWT("1")
            cookie.isHttpOnly = true
            cookie.path = "/api/"

            val newTherapySessionDTO = TherapySessionDTO(
                2,
                "testStatus",
                LocalDateTime.of(2023,10,12,20,0,0),
                LocalDateTime.of(2023,10,12,21,0,0),
                mutableListOf(2,3)
            )
            val expectedJson = "{\"error\": \"Wrong Users\"}"
            
            // when
            val performPut = mockMvc.put(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newTherapySessionDTO)
                cookie(cookie)
            }
            
            // then
            performPut.andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                    content { json(expectedJson) }
                }
        }
    }
    
    @Nested
    @DisplayName("GET api/therapySession\"")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetTherapySession {
        @Test
        fun `should GET all TherapySession of this user`() {
            // given
            val cookie = createJWT("1")
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
            val performGet = mockMvc.get(baseUrl) {
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