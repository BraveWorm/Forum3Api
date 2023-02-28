package com.forum.springboot.forum3API.repositorys

import com.forum.springboot.forum3API.models.UserRole
import org.springframework.data.jpa.repository.JpaRepository

interface UserRoleRepository: JpaRepository<UserRole, Long> {
}