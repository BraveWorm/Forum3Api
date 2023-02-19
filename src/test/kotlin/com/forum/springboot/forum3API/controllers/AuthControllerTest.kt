package com.forum.springboot.forum3API.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.forum.springboot.forum3API.dtos.UserRegisterDTO
import com.forum.springboot.forum3API.models.User
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
internal class AuthControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
){
    val baseUrl = "/api/auth"
    
    @Nested
    @DisplayName("api/auth/register")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class Register {


         @Test
         fun `should register the user`() {
             // given
             //val newUser = UserRegisterDTO("name","email@domain.com","password")
             val newUser = UserRegisterDTO("name","email@domain.com","password")
             // when
             val performPost = mockMvc.post("$baseUrl/register") {
                 contentType = MediaType.APPLICATION_JSON
                 content = objectMapper.writeValueAsString(newUser)
             }

             // then
             performPost.andDo { print() }
                 .andExpect {
                     status { isCreated() }
                     content {
                         contentType(MediaType.APPLICATION_JSON)
                         MockMvcResultMatchers.jsonPath("name",newUser.name)
                         MockMvcResultMatchers.jsonPath("email",newUser.email)
                     }
                 }
         }
        
        @Test
        fun `should return BAD REQUEST if user with given email already exists`() {
            // given
            val newUser = UserRegisterDTO("name","emailAlreadyInUse@domain.com","password")

            // when
            val performPost = mockMvc.post("$baseUrl/register") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newUser)
            }
            val performPost2 = mockMvc.post("$baseUrl/register") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newUser)
            }




            // then
            performPost.andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        MockMvcResultMatchers.jsonPath("name",newUser.name)
                        MockMvcResultMatchers.jsonPath("email",newUser.email)
                    }
                }

            performPost2.andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        MockMvcResultMatchers.jsonPath("message","email already in use")
                    }
                }
        }

        @Test
        fun `should return BAD REQUEST if email is invalid`() {
            // given
            val newUser = UserRegisterDTO("name","invalid email","password")

            // when
            val performPost = mockMvc.post("$baseUrl/register") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newUser)
            }

            // then
                performPost.andDo { print() }
                    .andExpect {
                        status { isBadRequest() }
                        content {
                            contentType(MediaType.APPLICATION_JSON)
                            MockMvcResultMatchers.jsonPath("email","email not valid")
                        }
                    }
        }

        @Test
        fun `should return BAD REQUEST if data is null`() {
            // given
            //val newUser = UserRegisterDTO(null,null,null)

            // when
            val performPost = mockMvc.post("$baseUrl/register") {
                contentType = MediaType.APPLICATION_JSON
                content = "{}"
            }

            // then
            performPost.andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        MockMvcResultMatchers.jsonPath("email","email not valid")
                        MockMvcResultMatchers.jsonPath("password","password can't be Null")
                        MockMvcResultMatchers.jsonPath("name","name can't be Null")
                    }
                }
        }
    }
}