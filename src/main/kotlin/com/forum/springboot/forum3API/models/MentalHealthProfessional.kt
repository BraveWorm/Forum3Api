package com.forum.springboot.forum3API.models

import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.*

@Entity
class MentalHealthProfessional(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false) var id: Long? = null,

    @Column() var specialization: String? = null,

    @Column() var hourlyRate: Double? = null,

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique=true)
    @JsonBackReference
    var user: User
)
