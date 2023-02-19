package com.forum.springboot.forum3API.models

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.Entity
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

@Entity
class User() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null


    @Column(nullable = false)
    @NotNull(message = "name can't be Null")
    var name:String? = null

    @Column(nullable = false, unique = true)
    @NotNull(message = "email can't be Null")
    @Email(message = "invalid email")
    var email: String? = null

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "password can't be Null")
    var password: String? = null
        set(value) {
            val passwordEncoder = BCryptPasswordEncoder()
            field = passwordEncoder.encode(value)
        }


    fun comparePassword(password: String): Boolean {
        return BCryptPasswordEncoder().matches(password, this.password)
    }
}