package com.forum.springboot.forum3API.dataloader.db

import com.forum.springboot.forum3API.models.User
import com.forum.springboot.forum3API.models.UserPersonalData
import com.forum.springboot.forum3API.repositorys.UserPersonalDataRepository
import com.forum.springboot.forum3API.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import java.time.LocalDate


@Component
class DataLoader @Autowired constructor(
    private val userService: UserService,
    private val userPersonalDataRepository: UserPersonalDataRepository
    ) : ApplicationRunner {
    override fun run(args: ApplicationArguments) {

        userService.save(User(null,"newUserForLogin@domain.com", "password"))
        userService.save(User(null,"emailAlreadyInUse@domain.com", "password"))

        userPersonalDataRepository.save(UserPersonalData(
            null,
            "firstName",
            "secondName",
            "lastName",
            "123456789",
            LocalDate.of(1999,10,13),
            "gender",
            userService.getById(1)
        ))

    }
}

