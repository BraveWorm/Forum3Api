package com.forum.springboot.forum3API.services


import com.forum.springboot.forum3API.models.User
import com.forum.springboot.forum3API.repositorys.UserRepository
import org.apache.coyote.http11.Constants.a
import org.springframework.stereotype.Service
import java.util.regex.Pattern
import javax.validation.constraints.Email
import kotlin.math.absoluteValue

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

    fun getById(id: Long?): User {
        if (id == null) throw IllegalArgumentException("param cannot be null.")
        return this.userRepository.getById(id)
    }

    fun getByMutableListOfIds(mutableListOfIds: MutableList<Long?>): MutableList<User> {
        val mutableListOfUser = arrayListOf<User>()
        mutableListOfIds.forEach {
            if (it == null) throw IllegalArgumentException("param cannot be null.");
            mutableListOfUser.add(this.userRepository.getById(it))
        }
        return mutableListOfUser
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