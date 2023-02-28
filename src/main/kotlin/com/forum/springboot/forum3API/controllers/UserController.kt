package com.forum.springboot.forum3API.controllers


import com.forum.springboot.forum3API.config.NotionConfigProperties
import com.forum.springboot.forum3API.dtos.Message
import com.forum.springboot.forum3API.models.User
import com.forum.springboot.forum3API.services.UserService
import io.jsonwebtoken.Jwts
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api/users")
class UserController (private val userService: UserService) {
    val notionConfigProperties = NotionConfigProperties()
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getUsers(): Collection<User> = userService.getUsers()

    @GetMapping("whoami")
    fun user(@CookieValue("jwt") jwt: String): ResponseEntity<Any> {
        try {

            if(jwt == null)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Message("unauthenticated"))

            val body = Jwts.parser().setSigningKey(notionConfigProperties.authToken).parseClaimsJws(jwt).body

            return ResponseEntity.ok(this.userService.getById(body.issuer.toLong()))
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Message("unauthenticated"))
        }
    }


//    @GetMapping("test")
//    @ResponseStatus(HttpStatus.OK)
//    fun test(@RequestBody body: UserRegisterDTO): UserDTO {
//
//        val shapeShift = ShapeShiftBuilder().build()
//        val userLoginDTO = UserLoginDTO("email","password")
//        return shapeShift.map(userLoginDTO,UserDTO::class.java)
//    }

//    @GetMapping("test")
//    @ResponseStatus(HttpStatus.OK)
//    fun test() {
//
//        val userDatasource = UserDatasource(userService)
//        userDatasource.addUsersToDB()
//    }





}