package com.mvp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SenderSpringBootStarterApplication

fun main(args: Array<String>) {
	runApplication<SenderSpringBootStarterApplication>(*args)
}
