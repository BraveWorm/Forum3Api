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
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
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
    @DisplayName("POST api/userpersonaldata")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class  AddUserPersonalData {


        @Test
        fun `should create UserPersonalData and return isCreated = true`() {
            // given
            // creating cookie/logging in
            val cookie = createJWT("3")
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
                3
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

        @Test
        fun `should return INTERNAL_SERVER_ERROR if UserPersonalData already exist in DB`() {
            // given
            // creating cookie/logging in
            val cookie = createJWT("4")
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
                4
            )

            // when
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newUserPersonalDataDTO)
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
    @DisplayName("PUT api/userpersonaldata")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class ChangeUserPersonalData {
        @Test
        fun `should change UserPersonalData and return status OK`() {
            // given
            // creating cookie/logging in
            val cookie = createJWT("4")
            cookie.isHttpOnly = true
            cookie.path = "/api/"

            val newUserPersonalDataDTO = UserPersonalDataDTO(
                null,
                "newFirstNameTest",
                null,
                "newLastNameTest",
                "123456789",
                LocalDate.of(1994, 11, 14),
                "newGenderTest",
                4
            )

            // when
            val performPut = mockMvc.put(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newUserPersonalDataDTO)
                cookie(cookie)
            }

            // then
            performPut.andDo { print() }
                .andExpect {
                    status { isOk() }
                }
        }

        @Test
        fun `should return BAD_REQUEST if there is no UserPersonalData in request`() {
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
    @DisplayName("GET api/userpersonaldata")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetUserPersonalData {    
        @Test
        fun `should get UserPersonalData and return status OK`() {
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