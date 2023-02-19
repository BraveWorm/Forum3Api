package com.forum.springboot.forum3API.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties
class NotionConfigProperties {
    val authToken: String="zfTRkarVNSyOY4BEK9XJCbfxk412rUTqUPT3QV16GTOPVDOQUguCSDyRHpaWr3M" //TODO store it somewhere safe
}