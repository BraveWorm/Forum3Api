package com.forum.springboot.forum3API.models

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*
import javax.persistence.*


@Entity
class Chat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false) var id: Long? = null,

    @Column()
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ") val creationDate: Date? = null,


    @ManyToMany(mappedBy = "userChat")
    val chatUser: MutableList<User> = mutableListOf(),

    @OneToMany
    @JoinColumn() private var message: MutableList<Message> = mutableListOf(),
)