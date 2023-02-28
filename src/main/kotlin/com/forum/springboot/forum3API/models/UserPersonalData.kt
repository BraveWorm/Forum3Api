package com.forum.springboot.forum3API.models

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
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
    @OneToOne var user: User
)