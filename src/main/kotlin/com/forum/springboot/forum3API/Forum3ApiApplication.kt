package com.forum.springboot.forum3API

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class Forum3ApiApplication

fun main(args: Array<String>) {
	runApplication<Forum3ApiApplication>(*args)
}
