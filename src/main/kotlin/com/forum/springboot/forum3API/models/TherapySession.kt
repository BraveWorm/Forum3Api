package com.forum.springboot.forum3API.models

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*
import javax.persistence.*

@Entity
class TherapySession(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false) var id: Long? = null,

    @Column() var status: String? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    @Column() var startOfTherapySession: Date? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    @Column() var endOfTherapySession: Date? = null,

    @ManyToMany(mappedBy = "userTherapySessions")
    val userTherapySession: MutableList<User> = mutableListOf(),

    @OneToOne
    @JoinColumn private val payment: Payment?,

)