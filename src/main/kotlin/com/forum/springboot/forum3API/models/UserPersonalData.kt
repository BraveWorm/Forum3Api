package com.forum.springboot.forum3API.models

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.forum.springboot.forum3API.services.UserService
import java.time.LocalDate
import java.util.*
import javax.persistence.*


@Entity
class UserPersonalData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false) var id: Long?,
    @Column() var firstName: String? = "",
    @Column() var secondName: String?,
    @Column() var lastName: String?,
    @Column() var phoneNumber: String?,
    @Column()
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") var dateOfBirth: LocalDate?,
    @Column() var gender: String?,

//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique=true)
    var user: User,
)