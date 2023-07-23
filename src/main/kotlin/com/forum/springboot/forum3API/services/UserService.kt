package com.forum.springboot.forum3API.services


import com.forum.springboot.forum3API.models.User
import com.forum.springboot.forum3API.repositorys.UserRepository
import org.springframework.stereotype.Service
import java.util.regex.Pattern
import javax.validation.constraints.Email

@Service
class UserService ( private val userRepository: UserRepository){

    fun getUsers(): Collection<User> {
        return userRepository.findAll()
    }

    fun findByEmail(email: String): User?{
        return this.userRepository.findByEmail(email)
    }

    fun save(user: User): User {
        return this.userRepository.save(user)
    }

    fun getById(id: Long): User {
        return this.userRepository.getById(id)
    }

    fun isEmailValid(@Email email: String): Boolean {
        return true
    }

    fun emailPatternMatches(emailAddress: String?, regexPattern: String?): Boolean {
        return Pattern.compile(regexPattern)
            .matcher(emailAddress)
            .matches()
    }

}