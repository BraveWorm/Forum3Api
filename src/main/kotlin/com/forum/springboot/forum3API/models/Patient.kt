package com.forum.springboot.forum3API.models

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

    @OneToOne var user: User
)