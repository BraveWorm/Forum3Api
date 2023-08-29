package com.forum.springboot.forum3API.dataloader.db

import com.forum.springboot.forum3API.models.*
import com.forum.springboot.forum3API.services.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime

const val ANSI_RED = "\u001B[31m"
const val ANSI_CYAN = "\u001B[36m"
const val ANSI_RESET = "\u001B[0m"
@Component
class DataLoader @Autowired constructor(
    private val userService: UserService,
    private val userPersonalDataService: UserPersonalDataService,
    private val userPatientService: PatientService,
    private val therapySessionService: TherapySessionService,
    private val mentalHealthProfessionalService: MentalHealthProfessionalService,
    ) : ApplicationRunner {
    override fun run(args: ApplicationArguments) {

//        userRoleService.save(UserRole(null, "UNREGISTERED"))
//        userRoleService.save(UserRole(null, "REGISTERED"))
//        userRoleService.save(UserRole(null, "PATIENT"))
//        userRoleService.save(UserRole(null, "MENTAL_HEALTH_PROFESSIONAL"))

//        userService.save(User(null,"newUserForLogin@domain.com", "password"))
        val user = User(null,"newUserForLogin@domain.com", "password")          //id = 1
        userService.save(user)
        userService.save(User(null,"emailAlreadyInUse@domain.com", "password")) //id = 2
        userService.save(User(null,"emailTesting01@domain.com", "password"))    //id = 3
        userService.save(User(null,"emailTesting02@domain.com", "password"))    //id = 4

        userPersonalDataService.save(UserPersonalData(
            null,
            "firstName",
            "secondName",
            "lastName",
            "123456789",
            LocalDate.of(1999,10,13),
            "gender",
            userService.getById(1)
        ))
        userPersonalDataService.save(UserPersonalData(
            null,
            "firstNameTest",
            null,
            "lastNameTest",
            "987456321",
            LocalDate.of(1993, 10, 13),
            "genderTest",
            userService.getById(4)
        ))

        userPatientService.save(Patient(
            null,
            "healthProblems",
        "therapeuticRequirements",
            userService.getById(1)
        ))


        therapySessionService.save(
            TherapySession(
            null,
            "status",
            LocalDateTime.of(2022,10,11,12,0,0),
            LocalDateTime.of(2022,10,11,13,0,0),
            mutableListOf( userService.getById(1),userService.getById(2) )
        ))

        therapySessionService.save(
            TherapySession(
                null,
                "status",
                LocalDateTime.of(2022,10,12,12,0,0),
                LocalDateTime.of(2022,10,12,13,0,0),
                mutableListOf( userService.getById(1),userService.getById(2))
            )
        )

//        mentalHealthProfessionalService.save(
//            MentalHealthProfessional(
//                null,
//                "specialization",
//                100.00,
//                userService.getById(3)
//            )
//        )

        mentalHealthProfessionalService.save(
            MentalHealthProfessional(
                null,
                "specialization",
                150.00,
                userService.getById(4)
            )
        )

        println(ANSI_CYAN + "Loading data completed" + ANSI_RESET)
    }
}

