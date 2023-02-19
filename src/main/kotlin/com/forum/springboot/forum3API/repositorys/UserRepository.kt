package com.forum.springboot.forum3API.repositorys

import com.forum.springboot.forum3API.models.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}
