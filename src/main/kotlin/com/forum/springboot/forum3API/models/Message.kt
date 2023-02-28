package com.forum.springboot.forum3API.models

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*
import javax.persistence.*


@Entity
class Message(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false) var id: Long? = null,

    @Column() val messageContent: String? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    @Column()  val messageDate: Date? = null,

    @ManyToOne
    @JoinColumn private var chat: Chat,

    @ManyToOne
    @JoinColumn private var user: User,
)