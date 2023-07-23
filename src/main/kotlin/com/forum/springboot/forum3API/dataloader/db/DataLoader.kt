package com.forum.springboot.forum3API.dataloader.db

import com.forum.springboot.forum3API.models.TherapySession
import com.forum.springboot.forum3API.models.User
import com.forum.springboot.forum3API.models.UserPersonalData
import com.forum.springboot.forum3API.services.TherapySessionService
import com.forum.springboot.forum3API.services.UserPersonalDataService
import com.forum.springboot.forum3API.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import java.time.LocalDate


@Component
class DataLoader @Autowired constructor(
    private val userService: UserService,
    private val userPersonalDataService: UserPersonalDataService,
    private val therapySessionService: TherapySessionService,
    //private val userRoleService: UserRoleService,
    ) : ApplicationRunner {
    override fun run(args: ApplicationArguments) {

//        userRoleService.save(UserRole(null, "UNREGISTERED"))
//        userRoleService.save(UserRole(null, "REGISTERED"))
//        userRoleService.save(UserRole(null, "PATIENT"))
//        userRoleService.save(UserRole(null, "MENTAL_HEALTH_PROFESSIONAL"))

        userService.save(User(null,"newUserForLogin@domain.com", "password"))
        userService.save(User(null,"emailAlreadyInUse@domain.com", "password"))

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

        val list: MutableList<User> = mutableListOf( userService.getById(1) )
        therapySessionService.save(TherapySession(
            null,
            "status",
            LocalDate.of(2022,10,10),
            LocalDate.of(2022,10,11),
            list
        ))

    }
}

