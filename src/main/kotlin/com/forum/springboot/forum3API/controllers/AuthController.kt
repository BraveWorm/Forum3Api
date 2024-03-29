package com.forum.springboot.forum3API.controllers

import com.forum.springboot.forum3API.config.NotionConfigProperties
import com.forum.springboot.forum3API.dtos.ResponseMessage
import com.forum.springboot.forum3API.dtos.UserLoginDTO
import com.forum.springboot.forum3API.models.User
import com.forum.springboot.forum3API.services.UserService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.hibernate.validator.internal.util.Contracts.assertTrue
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.function.Consumer
import java.util.regex.Pattern
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid


@RestController
@RequestMapping("api/auth")
class AuthController (private val userService: UserService) {

    val notionConfigProperties = NotionConfigProperties()

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(
        ex: MethodArgumentNotValidException
    ): Map<String, String?>? {
        val errors: MutableMap<String, String?> = HashMap()
        ex.bindingResult.allErrors.forEach(Consumer { error: ObjectError ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.getDefaultMessage()
            errors[fieldName] = errorMessage
        })
        return errors
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@Valid @RequestBody  body: User): Any {
        if (body.email?.let { this.userService.findByEmail(it) } != null)
            return ResponseEntity.badRequest().body(ResponseMessage("email already in use"))
        val regexPattern = "^(.+)@(\\S+)$"
        if(userService.emailPatternMatches(body.email, regexPattern))
            return this.userService.save(body)
//        val user = User()
//        user.email = body.email
//        user.name = body.name
//        user.password = body.password

        return ResponseEntity.badRequest().body(ResponseMessage("invalid email"))
    }

    fun patternMatches(emailAddress: String?, regexPattern: String?): Boolean {
        return Pattern.compile(regexPattern)
            .matcher(emailAddress)
            .matches()
    }

    @PostMapping("login")
    @ResponseStatus(HttpStatus.CREATED)
    fun login(@RequestBody body: UserLoginDTO, response: HttpServletResponse): ResponseEntity<Any> {
        val user = this.userService.findByEmail(body.email)
            ?: return ResponseEntity.badRequest().body(ResponseMessage("user not found!"))

        if(!user.comparePassword(body.password)){
            return ResponseEntity.badRequest().body(ResponseMessage("invalid password!"))
        }

        val issuer = user.id.toString()

        val jwt = Jwts.builder()
            .setIssuer(issuer)
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 *24))
            .signWith(SignatureAlgorithm.HS256, notionConfigProperties.authToken)
            .compact()

        val cookie = Cookie("jwt", jwt)
        cookie.isHttpOnly = true
        cookie.path = "/api/"

        response.addCookie(cookie)

        return ResponseEntity.ok(ResponseMessage("success"))
    }



    @PostMapping("logout")
    fun logout(response: HttpServletResponse): ResponseEntity<Any>{
        var cookie = Cookie("jwt", "")
        cookie.maxAge = 0

        response.addCookie(cookie)

        return ResponseEntity.ok(ResponseMessage("success"))
    }

}