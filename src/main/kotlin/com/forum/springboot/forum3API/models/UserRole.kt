package com.forum.springboot.forum3API.models

import javax.persistence.*

@Entity
class UserRole(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false) var id: Long? = null,

    @Column val userRole: String,

)