package com.example.HoneyClass

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class HoneyClassApplication

fun main(args: Array<String>) {
	runApplication<HoneyClassApplication>(*args)
}
