package com.forum.springboot.forum3API.models

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonManagedReference
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
class TherapySession(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false) var id: Long? = null,

    @Column() var status: String? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    @Column() var startOfTherapySession: LocalDate? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    @Column() var endOfTherapySession: LocalDate? = null,

//    @ManyToMany(mappedBy = "userTherapySessions")
//    val userTherapySession: MutableList<User> = mutableListOf()

    @ManyToMany
    @JsonBackReference
    @JoinTable(
        name = "user_therapy_session",
        joinColumns = [JoinColumn(name= "user_id")],
        inverseJoinColumns = [JoinColumn(name = "therapy_session_id")]
    )
    val userTherapySessions: MutableList<User> = mutableListOf(),
)