
# 🌱 Gradle Java Project Setup Guide

This guide covers how to:

* Initialize a Java project using Gradle
* Add dependencies
* Compile and run your code
* Use Gradle locally

---

## 🧱 1. Initializing a Gradle Project

### Option 1: Interactive Setup

If Gradle is installed on your system, you can initialize a new project with:

```bash
gradle init
```

Follow the prompts:

* **Project type:** `application`
* **DSL:** `Groovy` or `Kotlin`
* **Language:** `Java`
* **Test framework:** `JUnit`
* **Project name:** Your desired project name

### Option 2: Quick Start (Non-interactive)

```bash
gradle init --type java-application
```

This creates a folder structure:

```
your-project/
├── build.gradle
├── settings.gradle
├── gradle/
│   └── wrapper/
├── gradlew
├── gradlew.bat
└── src/
    ├── main/
    │   └── java/
    │       └── App.java
    └── test/
        └── java/
            └── AppTest.java
```

---

## 📦 2. Adding Dependencies

Open the `build.gradle` file and edit it like so:

```groovy
plugins {
    id 'java'
    id 'application'
}

group = 'com.example'
version = '1.0.0'

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter:3.2.5'
    testImplementation 'junit:junit:4.13.2'
}

application {
    mainClass = 'com.example.App' // Replace with your actual main class path
}
```

---

## ⚙️ 3. Java Compiler Configuration

You can specify the Java version using Gradle’s **Toolchain** support:

```groovy
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
```

To enable strict compilation warnings:

```groovy
tasks.withType(JavaCompile) {
    options.compilerArgs += ['-Xlint:all', '-Werror']
}
```

---

## 🏃‍♂️ 4. Using Gradle Locally

* **Build**:

```bash
./gradlew build
```

* **Run App**:

```bash
./gradlew run
```

* **Clean Output**:

```bash
./gradlew clean
```

* **Run Tests**:

```bash
./gradlew test
```

* **Generate JAR**:

```bash
./gradlew jar
```

---

## 🧪 Local Gradle & Java Compiler Setup (Extended Guide)

This section explains how to work with Gradle and the Java compiler **locally**, which is important for reproducible builds and CI/CD setups.

### 🛠️ Install Java (JDK)

Make sure Java is installed. Use JDK 17 or JDK 21 (LTS versions).

```bash
java -version
javac -version
```

If Java is not installed:

* **Ubuntu/Linux**:

  ```bash
  sudo apt update
  sudo apt install openjdk-21-jdk
  ```

* **MacOS**:

  ```bash
  brew install openjdk@21
  ```

* **Windows**:
  Download from [https://adoptium.net](https://adoptium.net)

### 🧰 Install Gradle

Check if Gradle is installed:

```bash
gradle -v
```

#### Option 1: Install via SDKMAN (Recommended)

```bash
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install gradle
```

#### Option 2: Manual Installation

1. Download from [https://gradle.org/releases/](https://gradle.org/releases/)
2. Unzip and set your `PATH`:

```bash
export PATH=$PATH:/path-to-gradle/bin
```

Add to `~/.bashrc` or `~/.zshrc`.

### 🧱 Use Gradle Locally (With Wrapper)

The Gradle wrapper (`gradlew`) allows every team member to use the same Gradle version:

```bash
gradle wrapper --gradle-version 8.7
```

This creates:

```
gradlew
gradlew.bat
gradle/wrapper/gradle-wrapper.properties
```

Now you can run:

```bash
./gradlew build
```

No need for a global Gradle installation after this!

### 🏗 Compile and Run with Gradle

Once the project is initialized, use these commands:

| Action       | Command           |
| ------------ | ----------------- |
| Build        | `./gradlew build` |
| Run App      | `./gradlew run`   |
| Clean Build  | `./gradlew clean` |
| Run Tests    | `./gradlew test`  |
| Generate JAR | `./gradlew jar`   |

You can also use:

```bash
java -jar build/libs/your-app.jar
```

To run the final packaged application.

---

## 3. Spring Core

Understand the foundation of the Spring Framework.

* **IoC / Dependency Injection**: Inversion of Control container, @Autowired, @Component
* **ApplicationContext**: Context initialization and lifecycle
* **Bean Lifecycle**: Bean creation and destruction callbacks
* **Spring Configuration**: Java-based configuration using @Configuration and @Bean

---

## 4. Spring Boot

Spring Boot simplifies Spring application development.

* **Auto-Configuration**: How Spring Boot configures components automatically
* **Starter Dependencies**: spring-boot-starter-web, starter-data-jpa, etc.
* **Configuration Properties**: application.properties / application.yml
* **Main Application Class**: Entry point with `@SpringBootApplication`
* **Actuator & Health Checks**: Endpoints for metrics, health, info, et
