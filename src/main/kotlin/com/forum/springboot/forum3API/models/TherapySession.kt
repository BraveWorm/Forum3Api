package com.forum.springboot.forum3API.models

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.forum.springboot.forum3API.dtos.TherapySessionDTO
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
class TherapySession(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false) var id: Long? = null,

    @Column() var status: String? = null,

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ", timezone="Europe/Warsaw")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    @Column() var startOfTherapySession: LocalDateTime? = null,

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ", timezone="Europe/Warsaw")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    @Column() var endOfTherapySession: LocalDateTime? = null,


    @ManyToMany
    @JsonBackReference
    @JoinTable(
        name = "user_therapy_session",
        joinColumns = [JoinColumn(name= "therapy_session_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    val userTherapySessions: MutableList<User> = mutableListOf(),
) {
    fun mapTherapySessionToTherapySessionDTO(): TherapySessionDTO {
        return TherapySessionDTO(
            this.id,
            this.status,
            this.startOfTherapySession,
            this.endOfTherapySession,
//            mutableListOfUserToMutableListOfUserId(this.userTherapySessions)
            this.userTherapySessions.map { user -> user.id }.toMutableList()
        )
    }
}


//    private fun mutableListOfUserToMutableListOfUserId(userTherapySessions: MutableList<User>): MutableList<Long?>{
//        val mutableListOfUserId = arrayListOf<Long?>()
//        userTherapySessions.forEach(){
//            mutableListOfUserId.add(it.id)
//        }
//        return mutableListOfUserId
//    }
//}
//    private fun mutableListOfUserToMutableListOfUserId(userTherapySessions: MutableList<User>): MutableList<Long?>{
//
//        return userTherapySessions.map { user -> user.id }.toMutableList()
//    }
//    private fun MutableList<User>.mapToUserIdList() = UserIdList(map { it.id }.toMutableList())
//
//}
//data class UserIdList(val listOfIds: MutableList<Long?>)