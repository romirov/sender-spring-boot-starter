plugins {
	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"
	id("org.springframework.boot") version "3.3.2"
	id("io.spring.dependency-management") version "1.1.6"
}

val projectGroup: String by project
val projectVersion: String by project

val kotestVersion: String by project
val kotestSpringVersion: String by project

dependencies {
	// Kotlin dependencies
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	// Spring Boot dependencies
	implementation("org.springframework.boot:spring-boot-starter")
	implementation ("org.springframework.kafka:spring-kafka")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
	testImplementation("io.kotest.extensions:kotest-extensions-spring:$kotestSpringVersion")
	testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
	testImplementation("io.kotest:kotest-property:$kotestVersion")
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
		group = projectGroup
		version = projectVersion
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test>().configureEach {
	useJUnitPlatform()
}