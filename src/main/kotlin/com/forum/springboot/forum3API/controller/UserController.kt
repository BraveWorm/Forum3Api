package com.forum.springboot.forum3API.controller


import com.forum.springboot.forum3API.model.User
import com.forum.springboot.forum3API.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api/users")
class UserController (private val service: UserService) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getUsers(): Collection<User> = service.getUsers()

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    fun postUsers(@RequestBody user: User): User = service.addUser(user)
//    {
//        val newUser =  User(null,"Adam", "email@test.com","superPassword")
//        userRepository.save(newUser)
//        return newUser
//    }
}