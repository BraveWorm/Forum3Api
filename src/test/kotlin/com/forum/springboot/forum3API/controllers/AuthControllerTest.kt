package com.forum.springboot.forum3API.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.forum.springboot.forum3API.dtos.ResponseMessage
import com.forum.springboot.forum3API.dtos.UserLoginDTO
import com.forum.springboot.forum3API.dtos.UserRegisterDTO
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


@SpringBootTest
@AutoConfigureMockMvc
internal class AuthControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
){
    val baseUrl = "/api/auth"



    @Nested
    @DisplayName("POST api/auth/register")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class Register {


         @Test
         fun `should register the user`() {

             // given
             val newUser = UserRegisterDTO("email@domain.com","password")
             val expectedJson = "{" +
                     "\"email\":\"${newUser.email}\"" +
                     "}"

             // when
             val performPost = mockMvc.post("$baseUrl/register") {
                 contentType = MediaType.APPLICATION_JSON
                 content = objectMapper.writeValueAsString(newUser)
             }

             // then
             performPost.andDo { print() }
                 .andExpect {
                     status { isCreated() }
                     content { json(expectedJson) }
                 }
         }
        
        @Test
        fun `should return BAD REQUEST if user with given email already exists`() {
            // given
            val newUser = UserRegisterDTO("emailAlreadyInUse@domain.com","password")
            val expectedJson = "{" +
                    "\"email\":\"${newUser.email}\"" +
                    "}"

            // when
            val performPost = mockMvc.post("$baseUrl/register") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newUser)
            }

            // then

            performPost.andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                    content { json(objectMapper.writeValueAsString(ResponseMessage("email already in use"))) }
                }
        }

        @Test
        fun `should return BAD REQUEST if email is invalid`() {
            // given
            val newUser = UserRegisterDTO(
                "invalid email",
                "password")
            val expectedJson = "{\"message\":\"invalid email\"}"

            // when
            val performPost = mockMvc.post("$baseUrl/register") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newUser)
            }

            // then
                performPost.andDo { print() }
                    .andExpect {
                        status { isBadRequest() }
                        content { json( expectedJson ) }
                    }
        }

//        @Test
//        fun `should return BAD REQUEST if there is no content in request`() {
//            // given
//            val noContentUser = "{}"
//            val expectedJson = "{" +
//                    "\"email\":\"email can't be Null\"," +
//                    "\"password\":\"password can't be Null\"" +
//                    "}"
//
//            // when
//            val performPost = mockMvc.post("$baseUrl/register") {
//                contentType = MediaType.APPLICATION_JSON
//                content = noContentUser
//            }
//
//            // then
//            performPost.andDo { print() }
//                .andExpect {
//                    status { isBadRequest() }
//                    content {
//                        json( expectedJson )
//                    }
//                }
//        }

    }

    @Nested
    @DisplayName("POST api/auth/login")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class Login {



        @Test
        fun `should login the user`() {
            // given
            val newUser = UserRegisterDTO("newUserForLogin@domain.com","password")
            val expectedJson = "{\"message\":\"success\"}"

            // when
            val performPostLogin = mockMvc.post("$baseUrl/login") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newUser)
            }

            // then
            performPostLogin.andDo { print() }
                .andExpect {
                    status { isOk() }
                    cookie { exists("jwt") }
                    content { json( expectedJson ) }
                }
        }

        @Test
        fun `should return BAD REQUEST when email is wrong`() {
            // given
            val userLoginDTO = UserLoginDTO("notExistingEmail@domain.com", "password")
            val expectedJson = "{\"message\":\"user not found!\"}"

            // when
            val performPostLogin = mockMvc.post("$baseUrl/login") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(userLoginDTO)
            }

            // then
            performPostLogin.andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                    cookie { doesNotExist("jwt") }
                    content { json( expectedJson ) }
                }
        }

        @Test
        fun `should return BAD REQUEST when password is wrong`() {
            // given
            val userLoginDTO = UserLoginDTO("newUserForLogin@domain.com", "wrongPassword")
            val expectedJson = "{\"message\":\"invalid password!\"}"

            // when
            val performPostLogin = mockMvc.post("$baseUrl/login") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(userLoginDTO)
            }

            // then
            performPostLogin.andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                    cookie { doesNotExist("jwt") }
                    content { json( expectedJson ) }
                }
        }

    }
}