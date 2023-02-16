package com.forum.springboot.forum3API.service


import com.forum.springboot.forum3API.model.User
import com.forum.springboot.forum3API.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService ( private val repository: UserRepository){

    fun getUsers(): Collection<User> {
        return repository.findAll()
    }

    fun addUser(user: User): User = repository.save(user)
}