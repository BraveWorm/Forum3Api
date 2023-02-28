package com.forum.springboot.forum3API.models

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

@Entity
@Table(name="users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false) var id: Long? = null,

    @Column(nullable = false, unique = true)
    @NotNull(message = "email can't be Null")
    @Email(message = "invalid email") var email: String? = null,

    @ManyToOne
    @JoinColumn private val userRole: UserRole? = null,

    @OneToMany
    @JoinColumn private val message: MutableList<Message> = mutableListOf(),

    @ManyToMany
    @JoinTable(
        name = "user_chat",
        joinColumns = [JoinColumn()],
        inverseJoinColumns = [JoinColumn()]
    )
    val userChat: MutableList<Chat> = mutableListOf(),

    @ManyToMany
    @JoinTable(
        name = "user_therapy_session",
        joinColumns = [JoinColumn()],
        inverseJoinColumns = [JoinColumn()]
    )
    val userTherapySessions: MutableList<TherapySession> = mutableListOf(),
) {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "password can't be Null")
    @Column(nullable = false)
    var password: String? = ""
        set(value) {
            val passwordEncoder = BCryptPasswordEncoder()
            field = passwordEncoder.encode(value)
        }

    constructor(
        id: Long?,
        email: String?,
        password: String?
    ) : this() {
        this.id = id
        this.email = email
        this.password = password
    }

    fun comparePassword(password: String): Boolean {
        return BCryptPasswordEncoder().matches(password, this.password)
    }

}