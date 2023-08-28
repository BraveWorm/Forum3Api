package com.forum.springboot.forum3API.models

import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.*


@Entity
class Patient(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @Lob
    @Column() val healthProblems: String? = null,

    @Lob
    @Column() val therapeuticRequirements: String? = null,

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique=true)
    @JsonBackReference
    var user: User
)