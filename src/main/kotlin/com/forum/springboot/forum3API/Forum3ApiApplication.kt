package com.forum.springboot.forum3API

import com.forum.springboot.forum3API.config.NotionConfigProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
@EnableConfigurationProperties(NotionConfigProperties::class)
class Forum3ApiApplication

fun main(args: Array<String>) {
	runApplication<Forum3ApiApplication>(*args)
}
