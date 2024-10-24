plugins {
    id("org.springframework.boot") version "3.2.0" // Use the latest version
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.8.0" // Include Kotlin if necessary
    id("application") // Add the application plugin
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17 // Specify your Java version

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web") // For web applications
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
    useJUnitPlatform()
}

// Configure the application plugin
application {
    mainClass.set("com.example.demo.DemoApplication") // Change this to your main class
}
