package com.forum.springboot.forum3API.models

import com.fasterxml.jackson.annotation.JsonBackReference
import com.forum.springboot.forum3API.dtos.MentalHealthProfessionalDTO
import com.forum.springboot.forum3API.dtos.PatientDTO
import dev.krud.shapeshift.enums.AutoMappingStrategy
import dev.krud.shapeshift.resolver.annotation.AutoMapping
import javax.persistence.*

@Entity
@AutoMapping(MentalHealthProfessionalDTO::class, strategy = AutoMappingStrategy.BY_NAME)
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
) {
    fun mapMentalHealthProfessionalToMentalHealthProfessionalDTO(): MentalHealthProfessionalDTO {
        return MentalHealthProfessionalDTO(
            this.id,
            this.specialization,
            this.hourlyRate,
            this.user.id
        )
    }
}
