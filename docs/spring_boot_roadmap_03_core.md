**3. Spring Core** from the Spring Boot Roadmap:

---

## 🧠 3. Spring Core – In Depth

Spring Core is the foundation of the Spring Framework. 
It provides the core features for dependency injection (DI), 
bean management, and application configuration.

---

### 1. **IoC / Dependency Injection**

#### 🌀 Inversion of Control (IoC)

* **Definition**: IoC is a design principle where 
* the control of object creation and lifecycle is 
* transferred from the application code to the Spring container.
* Instead of your classes creating their own dependencies (`new SomeService()`), 
* Spring does this and injects them where needed.

#### 🧩 Dependency Injection (DI)

* DI is the actual implementation of IoC in Spring.
* You define dependencies and Spring "injects" them into your classes, typically via:

    * **Constructor injection** (recommended)
    * **Field injection**
    * **Setter injection**

#### 🏷 Common Annotations:

* `@Component`: Marks a class as a Spring-managed bean.
* `@Service`, `@Repository`, `@Controller`: Specializations of `@Component` for specific layers.
* `@Autowired`: Tells Spring to inject a bean (dependency) automatically.

#### ✅ Example:

```java
@Component
public class UserService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
}
```

---

### 2. **ApplicationContext**

#### 🔄 What is it?

* The **ApplicationContext** is the central interface for accessing the Spring IoC container.
* It:

    * Instantiates beans
    * Manages their lifecycle
    * Resolves dependencies
    * Provides resource and event handling

#### 📌 Common implementations:

* `AnnotationConfigApplicationContext`: For Java-based config
* `ClassPathXmlApplicationContext`: For XML config (legacy)

#### 🧪 Example:

```java
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
MyService service = context.getBean(MyService.class);
```

---

### 3. **Bean Lifecycle**

#### 🧬 Lifecycle Stages:

1. **Instantiation**: Object is created using a constructor.
2. **Dependency Injection**: Spring injects dependencies via constructors, setters, or fields.
3. **Initialization**: After dependencies are injected, initialization logic runs.
4. **Usage**: Bean is ready for use.
5. **Destruction**: When the container shuts down, destruction callbacks are executed.

#### 🛠 Lifecycle Hooks:

* `@PostConstruct`: Runs after the bean is fully initialized.
* `@PreDestroy`: Runs before the bean is destroyed.
* `InitializingBean` / `DisposableBean`: Interfaces for custom init/destroy logic.
* `initMethod` / `destroyMethod`: Declared in `@Bean`.

#### ✅ Example:

```java
@Component
public class ResourceManager {
    @PostConstruct
    public void init() {
        System.out.println("Initializing resource");
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("Cleaning up resource");
    }
}
```

---

### 4. **Spring Configuration**

#### 🛠 Configuration Approaches:

* **XML-based**: Legacy, not commonly used anymore
* **Java-based (preferred)**: Type-safe and IDE-friendly

#### 🧾 Java Config Example:

```java
@Configuration
public class AppConfig {
    
    @Bean
    public MyService myService() {
        return new MyService(myRepository());
    }

    @Bean
    public MyRepository myRepository() {
        return new MyRepository();
    }
}
```

#### 🔌 What Happens:

* Spring sees `@Configuration` and processes all `@Bean` methods
* These beans are registered in the `ApplicationContext`

---

This is an excellent and well-structured explanation of **Spring Core** concepts! Below, I’ll provide additional insights, best practices, and clarifications to enhance your understanding.

---

## 🔍 **Deep Dive & Best Practices**

### 1. **IoC / Dependency Injection**
#### 📌 **Constructor Injection vs. Field/Setter Injection**
- **✅ Constructor Injection (Recommended)**
    - Ensures immutability (dependencies are `final`).
    - Better for testing (no reflection needed).
    - Clearly declares mandatory dependencies.

- **⚠️ Field/Setter Injection (Use Sparingly)**
    - **Field Injection** (`@Autowired` on fields) is discouraged because:
        - Makes testing harder (requires Spring or reflection).
        - Hides dependencies (no constructor signature).
    - **Setter Injection** can be useful for optional dependencies.

#### 🎯 **Best Practice**
```java
@Service
public class OrderService {
    private final PaymentService paymentService; // Immutable
    
    // No @Autowired needed if only one constructor (implicit since Spring 4.3)
    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
```
> **Note**: If using Lombok, `@RequiredArgsConstructor` can reduce boilerplate for constructor injection.

---

### 2. **ApplicationContext**
#### 🔄 **Types of `ApplicationContext`**
| Implementation | Use Case |
|---------------|----------|
| `AnnotationConfigApplicationContext` | Java-based config (modern apps) |
| `ClassPathXmlApplicationContext` | Legacy XML config (older apps) |
| `FileSystemXmlApplicationContext` | Loads XML config from file system |
| `WebApplicationContext` | Web-aware context (Spring MVC) |

#### 💡 **Key Methods**
```java
ApplicationContext ctx = ...;
ctx.getBean(MyService.class); // Fetch bean by type
ctx.getBean("myBeanName"); // Fetch bean by name
ctx.getBeansOfType(MyService.class); // Get all beans of a type
```

---

### 3. **Bean Lifecycle**
#### 🧬 **Lifecycle Flowchart**
```
1. Bean Instantiation → 2. Populate Properties → 3. BeanPostProcessor (pre-init) →  
4. @PostConstruct → 5. InitializingBean.afterPropertiesSet() → 6. Custom initMethod →  
7. BeanPostProcessor (post-init) → 8. Bean is Ready → 9. @PreDestroy → 10. DisposableBean.destroy()
```

#### 🛠 **When to Use Which?**
- **`@PostConstruct`/`@PreDestroy`**: Preferred for custom logic (JSR-250 standard).
- **`InitializingBean`/`DisposableBean`**: Rarely used (tightly couples to Spring).
- **`initMethod`/`destroyMethod`**: Useful for third-party library beans.

#### ✅ **Example with All Hooks**
```java
@Component
public class CacheManager implements InitializingBean, DisposableBean {
    
    @PostConstruct
    public void setup() { System.out.println("PostConstruct"); }
    
    @Override
    public void afterPropertiesSet() { System.out.println("InitializingBean"); }
    
    public void customInit() { System.out.println("initMethod"); }
    
    @PreDestroy
    public void cleanup() { System.out.println("PreDestroy"); }
    
    @Override
    public void destroy() { System.out.println("DisposableBean"); }
}

@Configuration
public class AppConfig {
    @Bean(initMethod = "customInit", destroyMethod = "cleanup")
    public CacheManager cacheManager() { return new CacheManager(); }
}
```
**Output on Startup**:
```
PostConstruct → InitializingBean → initMethod
```
**Output on Shutdown**:
```
PreDestroy → DisposableBean
```

---

### 4. **Spring Configuration**
#### 🏆 **Best Practices**
1. **Prefer `@ComponentScan` over manual `@Bean` definitions** for most cases.
2. **Use `@Bean` for:**
    - Third-party library beans (e.g., `DataSource`, `RestTemplate`).
    - Conditional bean creation (`@Profile`, `@Conditional`).
3. **Avoid XML config** unless maintaining legacy code.

#### 🔄 **Mixing Config Styles**
```java
@Configuration
@ImportResource("classpath:legacy-config.xml") // Import XML into Java config
public class AppConfig { ... }
```

---

## ❓ **Common Pitfalls & Solutions**
1. **Circular Dependencies**
    - **Symptom**: `BeanCurrentlyInCreationException`.
    - **Fix**:
        - Redesign to avoid circular refs (extract shared logic).
        - Use `@Lazy` on one dependency.

2. **Missing Dependencies**
    - **Cause**: No matching bean found for `@Autowired`.
    - **Check**:
        - Is the class annotated (`@Component`, `@Service`, etc.)?
        - Is it in a scanned package (`@ComponentScan`)?

3. **Bean Scope Issues**
    - **Problem**: Accidentally using `@Scope("prototype")` when singleton is needed.
    - **Tip**: Most beans should be singletons (default).

---

## 🚀 **Next Steps**
- **Explore**:
    - **AOP (Aspect-Oriented Programming)**: `@Aspect`, `@Around`, `@Transactional`.
    - **Spring Expression Language (SpEL)**: Dynamic bean wiring.
- **Practice**: Build a small project using only Java config and constructor injection.

This deepens your mastery of **Spring Core**—the backbone of Spring Boot! Would you like a practical exercise to reinforce these concepts? 😊