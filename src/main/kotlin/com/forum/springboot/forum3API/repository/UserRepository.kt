package com.forum.springboot.forum3API.repository

import com.forum.springboot.forum3API.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
}
