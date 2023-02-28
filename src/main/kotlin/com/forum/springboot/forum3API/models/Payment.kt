package com.forum.springboot.forum3API.models

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*
import javax.persistence.*

@Entity
class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false) var id: Long? = null,


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    @Column() var paymentDate: Date? = null,

    @Column() var paymentMethod: String? = null,

    @OneToOne var therapySession: TherapySession,
)