package com.forum.springboot.forum3API.model

import javax.persistence.Entity
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @Column(nullable = false)
    val name = ""

    @Column(nullable = false, unique = true)
    val email = ""

    @Column(nullable = false)
    val hashedPassword = ""
}