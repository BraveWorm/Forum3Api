package com.forum.springboot.forum3API.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain


//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
//import org.springframework.security.crypto.password.PasswordEncoder
//import org.springframework.security.web.SecurityFilterChain

//@Configuration
//@EnableWebSecurity
class SecurityConfig(){

    @Bean
    @Throws(java.lang.Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http.authorizeRequests().antMatchers("/**").permitAll();
        return http.build()
    }
//    @Autowired
//    @Throws(Exception::class)
//    fun configureGlobal(auth: AuthenticationManagerBuilder) {
//        auth.inMemoryAuthentication().withUser("user")
//            .password(passwordEncoder().encode("password")).roles("USER")
//    }
//
//    @Bean
//    fun passwordEncoder(): PasswordEncoder? {
//        return BCryptPasswordEncoder()
//    }
//
//    @Bean
//    @Throws(java.lang.Exception::class)
//    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
//        http.authorizeRequests()
//            .anyRequest().authenticated()
//            .and().httpBasic()
//        return http.build()
//    }


}