import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
	id("org.springframework.boot") version "2.7.6"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	id("org.jetbrains.kotlin.plugin.jpa") version "1.6.21"
	id("org.jetbrains.kotlin.plugin.noarg") version "1.6.21"
	id("org.jetbrains.kotlin.plugin.allopen") version "1.4.32"
}

allOpen {
	annotation("com.example.HoneyClass.annotation.AllOpen")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	// MySQL connector 및 JPA
	implementation("mysql:mysql-connector-java")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
