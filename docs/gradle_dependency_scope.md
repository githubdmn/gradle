# Gradle Dependency Scopes Explained

Here's a detailed explanation of each dependency scope in Gradle/Spring Boot:

## **1. `implementation`**
**Purpose:** Main dependencies needed to compile and run your application.

**What it does:**
- Included in compile classpath AND runtime classpath
- Available to your main source code
- **Not** exposed to dependent modules (prevents transitive dependency leaks)

**When to use:**
- Core libraries your app needs (Spring Boot starters, utilities, etc.)
- Dependencies your code directly uses

**Example:**
```gradle
implementation("org.springframework.boot:spring-boot-starter-web")
```
```java
// You can use these classes in your code
import org.springframework.web.bind.annotation.RestController;

@RestController  // ✓ Available
public class MyController { }
```

---

## **2. `compileOnly`**
**Purpose:** Dependencies needed only at compile time, **not at runtime**.

**What it does:**
- Included only in compile classpath
- **Not** included in runtime classpath or distribution
- Used by annotation processors at compile time

**When to use:**
- Annotation processors (Lombok, MapStruct, etc.)
- APIs that are provided by the runtime environment (e.g., Servlet API in Tomcat)
- Code generation tools

**Example:**
```gradle
compileOnly("org.projectlombok:lombok")
```
```java
import lombok.Data;  // ✓ Available at compile time

@Data  // Lombok generates getters/setters at compile time
public class User {
    private String name;
}

// At runtime: No lombok.jar in your application
```

---

## **3. `annotationProcessor`**
**Purpose:** Annotation processors that generate code at compile time.

**What it does:**
- Processes annotations to generate code
- Runs during compilation
- **Not** included in runtime classpath

**When to use:**
- Lombok annotation processor
- MapStruct annotation processor
- Any annotation-based code generation

**Example:**
```gradle
annotationProcessor("org.projectlombok:lombok")
annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
```

**Flow:**
```
1. You write: @Data class User { ... }
2. annotationProcessor runs during compile
3. Generates: User.class with getters/setters
4. lombok.jar not included in final JAR
```

---

## **4. `runtimeOnly`**
**Purpose:** Dependencies needed only at runtime, **not at compile time**.

**What it does:**
- **Not** included in compile classpath
- Only included in runtime classpath
- Your code cannot import/use these classes directly

**When to use:**
- Database drivers (MySQL, PostgreSQL, etc.)
- Runtime libraries
- Implementation details that your code doesn't directly reference

**Example:**
```gradle
runtimeOnly("com.mysql:mysql-connector-j")
```
```java
// You CANNOT do this:
import com.mysql.cj.jdbc.Driver;  // ✗ Compile error!

// But Spring Boot can load it at runtime:
// Class.forName("com.mysql.cj.jdbc.Driver")
```

---

## **5. `developmentOnly`**
**Purpose:** Dependencies needed only during development.

**What it does:**
- Included in development environment
- **Not** included in production builds
- Removed when building JAR/WAR files

**When to use:**
- Development tools (spring-boot-devtools)
- Hot-reload functionality
- Development-time debugging tools

**Example:**
```gradle
developmentOnly("org.springframework.boot:spring-boot-devtools")
```
- Enables automatic restart when code changes
- Not included in `./gradlew build` output

---

## **6. `testImplementation`**
**Purpose:** Dependencies needed only for testing.

**What it does:**
- Available only in test source set
- **Not** included in production runtime

**When to use:**
- Testing frameworks (JUnit, Mockito, Testcontainers)
- Test utilities

**Example:**
```gradle
testImplementation("org.springframework.boot:spring-boot-starter-test")
```
```java
// Only in src/test/java/
@Test  // JUnit annotation
public void testSomething() { }
```

---

## **Visual Comparison:**

| Scope | Compile | Runtime | Production | Transitive |
|-------|---------|---------|------------|------------|
| `implementation` | ✅ | ✅ | ✅ | Limited |
| `compileOnly` | ✅ | ❌ | ❌ | ❌ |
| `annotationProcessor` | ✅ | ❌ | ❌ | ❌ |
| `runtimeOnly` | ❌ | ✅ | ✅ | ✅ |
| `developmentOnly` | ✅ | ✅ | ❌ | ✅ |
| `testImplementation` | ❌ | ❌ | ❌ | ✅ |

---

## **Real-World Example from Your `build.gradle`:**

```gradle
dependencies {
    // ✅ Implementation: Your app needs Spring Web to run
    implementation("org.springframework.boot:spring-boot-starter-web")
    
    // ✅ CompileOnly: You need Lombok annotations at compile time
    compileOnly("org.projectlombok:lombok")
    
    // ✅ AnnotationProcessor: Lombok needs to process @Data annotations
    annotationProcessor("org.projectlombok:lombok")
    
    // ✅ RuntimeOnly: MySQL driver needed at runtime, not compile time
    runtimeOnly("com.mysql:mysql-connector-j")
    
    // ✅ DevelopmentOnly: Hot reload only in development
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    
    // ✅ testImplementation: Tests need Mockito
    testImplementation("org.mockito:mockito-core")
}
```

---

## **Common Patterns:**

### **1. Lombok Setup:**
```gradle
compileOnly("org.projectlombok:lombok")        // For @Data, @Getter, etc.
annotationProcessor("org.projectlombok:lombok") // To generate code
```

### **2. MapStruct Setup:**
```gradle
implementation("org.mapstruct:mapstruct:1.6.3")          // MapStruct annotations
annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3") // Code generation
```

### **3. Database Setup:**
```gradle
implementation("org.springframework.boot:spring-boot-starter-data-jpa") // JPA API
runtimeOnly("com.mysql:mysql-connector-j")                              // MySQL impl
```

### **4. Testing Setup:**
```gradle
testImplementation("org.springframework.boot:spring-boot-starter-test") // Test framework
testImplementation("com.h2database:h2")                                 // Test DB
```

---

## **Dependency Resolution Flow:**

```
Your Code (src/main/java)
       ↓
  compileOnly       ← Lombok annotations
  implementation    ← Spring Boot, OpenCSV
  annotationProcessor ← Processes annotations
       ↓
  Compilation
       ↓
  runtimeOnly       ← MySQL driver added
  developmentOnly   ← DevTools added (dev only)
       ↓
  Runtime Classpath
       ↓
testImplementation  ← Added for tests only
```

---

## **Key Benefits:**

1. **Smaller JARs:** `compileOnly` and `developmentOnly` dependencies aren't packaged
2. **Cleaner APIs:** `runtimeOnly` hides implementation details
3. **Faster Compilation:** Annotation processors run only when needed
4. **Environment-specific:** Different dependencies for dev/test/prod

---

## **Common Mistakes to Avoid:**

❌ **Wrong:** Using `implementation` for database drivers
```gradle
// Don't do this:
implementation("com.mysql:mysql-connector-j")  // ❌

// Do this:
runtimeOnly("com.mysql:mysql-connector-j")     // ✅
```

❌ **Wrong:** Missing `annotationProcessor`
```gradle
compileOnly("org.projectlombok:lombok")  // ❌ Need annotationProcessor too!
annotationProcessor("org.projectlombok:lombok")  // ✅ Required
```

❌ **Wrong:** Using `implementation` for test dependencies
```gradle
implementation("org.mockito:mockito-core")  // ❌ Test-only in production!

testImplementation("org.mockito:mockito-core")  // ✅ Only in tests
```

