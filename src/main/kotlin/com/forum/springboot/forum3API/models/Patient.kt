package com.forum.springboot.forum3API.models

import com.fasterxml.jackson.annotation.JsonBackReference
import com.forum.springboot.forum3API.dtos.PatientDTO
import dev.krud.shapeshift.enums.AutoMappingStrategy
import dev.krud.shapeshift.resolver.annotation.AutoMapping
import javax.persistence.*


@Entity
@AutoMapping(Patient::class, strategy = AutoMappingStrategy.BY_NAME)
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
) {
    fun mapPatientToPatientDTO(): PatientDTO {
        return PatientDTO(
            this.id,
            this.healthProblems,
            this.therapeuticRequirements,
            this.user.id
        )
    }
}