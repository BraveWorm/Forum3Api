package com.forum.springboot.forum3API.dataloader.db

import com.forum.springboot.forum3API.models.Patient
import com.forum.springboot.forum3API.models.TherapySession
import com.forum.springboot.forum3API.models.User
import com.forum.springboot.forum3API.models.UserPersonalData
import com.forum.springboot.forum3API.services.PatientService
import com.forum.springboot.forum3API.services.TherapySessionService
import com.forum.springboot.forum3API.services.UserPersonalDataService
import com.forum.springboot.forum3API.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import java.time.LocalDate

const val ANSI_RED = "\u001B[31m"
const val ANSI_CYAN = "\u001B[36m"
const val ANSI_RESET = "\u001B[0m"
@Component
class DataLoader @Autowired constructor(
    private val userService: UserService,
    private val userPersonalDataService: UserPersonalDataService,
    private val userPatientService: PatientService,
    private val therapySessionService: TherapySessionService,
    //private val userRoleService: UserRoleService,
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


        val list: MutableList<User> = mutableListOf( userService.getById(1),userService.getById(2) )
//        therapySessionService.save(TherapySession(
//            null,
//            "status",
//            LocalDate.of(2022,10,10),
//            LocalDate.of(2022,10,11),
//            list
//        ))
        val therapySession = TherapySession(
            null,
            "status",
            LocalDate.of(2022,10,10),
            LocalDate.of(2022,10,11),
            list
        )
        therapySessionService.save(therapySession)
//        user.userTherapySessions.add(therapySession)
//        userService.save(user)



        println(ANSI_CYAN + "Loading data completed" + ANSI_RESET)
    }
}

