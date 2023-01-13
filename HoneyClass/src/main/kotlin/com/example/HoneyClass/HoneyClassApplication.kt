package com.example.HoneyClass

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
class HoneyClassApplication

fun main(args: Array<String>) {
	runApplication<HoneyClassApplication>(*args)
}
