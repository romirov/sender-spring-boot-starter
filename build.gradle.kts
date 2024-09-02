plugins {
	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"
	id("org.springframework.boot") version "3.3.2"
	id("io.spring.dependency-management") version "1.1.6"
}

val projectGroup: String by project
val projectVersion: String by project

dependencies {
	// Kotlin dependencies
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation(libs.jacksonDatabind)

	// Spring Boot dependencies
	implementation("org.springframework.boot:spring-boot-starter")
	implementation ("org.springframework.kafka:spring-kafka")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation ("org.springframework.kafka:spring-kafka-test")
	testImplementation(libs.kotest.runner.junit5)
	testImplementation(libs.kotest.assertions.core)
	testImplementation(libs.kotest.property)
	testImplementation(libs.kotest.extensions.spring)
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