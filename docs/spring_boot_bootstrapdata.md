## **How Spring Knows When to Execute `BootstrapData`:**

Spring executes this code **automatically on application startup** through two key mechanisms:

### **1. `CommandLineRunner` Interface:**
```java
public class BootstrapData implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        // This runs automatically on startup
    }
}
```

- `CommandLineRunner` is a Spring Boot interface
- Any bean implementing this interface has its `run()` method **automatically called**
- Called **after** the application context is fully initialized
- Receives the same command-line arguments as the `main()` method

### **2. `@Component` Annotation:**
```java
@Component
public class BootstrapData implements CommandLineRunner {
    // ...
}
```

- `@Component` marks this class as a **Spring bean**
- Spring scans and detects it during component scanning
- Creates an instance and manages its lifecycle

## **Execution Order on Startup:**

```
1. Application starts (main method)
2. Spring context initialization
3. Bean creation and dependency injection
4. Database setup (Flyway migrations run)
5. All CommandLineRunner beans execute (your BootstrapData)
6. Application is ready
```

## **Key Points:**

### **A. Automatic Detection:**
```java
// Spring Boot automatically:
// 1. Scans for @Component, @Service, @Repository, @Controller
// 2. Finds BootstrapData (implements CommandLineRunner)
// 3. Calls run() method after context is ready
```

### **B. Why It Runs:**
- Spring Boot has built-in support for `CommandLineRunner` and `ApplicationRunner`
- These interfaces are designed for **startup tasks**
- Perfect for: database seeding, cache warming, initialization

### **C. Multiple Runners:**
You can have multiple `CommandLineRunner` beans. Spring executes them in order:
```java
@Component
@Order(1)  // Runs first
public class FirstRunner implements CommandLineRunner { }

@Component
@Order(2)  // Runs second  
public class SecondRunner implements CommandLineRunner { }
```

### **D. `@Transactional` Annotation:**
```java
@Transactional
@Override
public void run(String... args) throws Exception {
    // Runs in a single transaction
}
```
- Wraps the entire `run()` method in a database transaction
- If any error occurs, all database changes are rolled back
- Good for data seeding to ensure data consistency

## **Lifecycle Visualization:**

```
┌─────────────────────────────────────────────────────────────┐
│                 Spring Boot Startup Flow                     │
├─────────────────────────────────────────────────────────────┤
│ 1. SpringApplication.run(DemoApplication.class, args)       │
│    ↓                                                        │
│ 2. Context Initialization                                   │
│    - Bean scanning (@Component, @Service, etc.)             │
│    - Creates BootstrapData bean                             │
│    - Injects dependencies (repositories, services)          │
│    ↓                                                        │
│ 3. Database Setup                                           │
│    - Flyway migrations run (if configured)                  │
│    - Hibernate DDL (if configured)                          │
│    ↓                                                        │
│ 4. Execute CommandLineRunners                               │
│    ┌─────────────────────────────────────────────────────┐  │
│    │ BootstrapData.run()                                 │  │
│    │  ↓                                                 │  │
│    │  loadBeerData()                                    │  │
│    │  loadCsvData()                                     │  │
│    │  loadCustomerData()                                │  │
│    └─────────────────────────────────────────────────────┘  │
│    ↓                                                        │
│ 5. Application Ready!                                       │
│    - Tomcat started on port 8080                           │
│    - REST endpoints available                               │
└─────────────────────────────────────────────────────────────┘
```

## **What Triggers the Execution:**

### **Spring Boot's `SpringApplication` class:**
```java
public class SpringApplication {
    public ConfigurableApplicationContext run(String... args) {
        // ... initialization ...
        
        // This line finds and calls all CommandLineRunners
        callRunners(context, args);
        
        // ... rest of startup ...
    }
    
    private void callRunners(ApplicationContext context, String[] args) {
        // Gets ALL beans implementing CommandLineRunner
        Map<String, CommandLineRunner> runners = 
            context.getBeansOfType(CommandLineRunner.class);
        
        // Sorts them by @Order annotation
        List<CommandLineRunner> sortedRunners = new ArrayList<>(runners.values());
        AnnotationAwareOrderComparator.sort(sortedRunners);
        
        // Executes each runner
        for (CommandLineRunner runner : sortedRunners) {
            runner.run(args);
        }
    }
}
```

## **When It Doesn't Run:**

The code **won't run** if:
1. **Bean is not created** (missing `@Component` or not in component scan)
2. **Profile is inactive** (if using `@Profile("dev")` and not running with dev profile)
3. **Conditional bean** (if using `@ConditionalOnProperty` and property not set)
4. **Application fails to start** before reaching this point

## **Alternative: `ApplicationRunner`**

Similar to `CommandLineRunner` but receives `ApplicationArguments`:
```java
@Component
public class BootstrapData implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // args provides parsed command-line arguments
        // Can access: args.getOptionNames(), args.getNonOptionArgs(), etc.
    }
}
```

## **Your Specific Code Flow:**

```java
@Component  // 1. Spring detects this as a bean
@RequiredArgsConstructor  // 2. Lombok creates constructor for dependency injection
public class BootstrapData implements CommandLineRunner {  // 3. Implements interface
    
    // 4. Spring injects these dependencies
    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;
    private final BeerCsvService beerCsvService;
    
    @Transactional  // 5. Transaction wraps entire run() method
    @Override
    public void run(String... args) throws Exception {  // 6. Called automatically
        loadBeerData();      // 7. Your custom methods execute
        loadCsvData();
        loadCustomerData();
    }
    
    private void loadCsvData() {
        if (beerRepository.count() < 10) {  // 8. Only runs if DB is empty
            // 9. Loads CSV and saves to database
        }
    }
}
```

## **Summary:**

Spring knows to execute your `BootstrapData` because:
1. **It's a `@Component`** → Spring creates it as a bean
2. **Implements `CommandLineRunner`** → Spring automatically calls `run()` on startup
3. **Timing** → Executes after context is ready but before application is fully available
4. **Order** → Can control execution order with `@Order` annotation

This pattern is perfect for:
- Database seeding/initialization
- Loading reference data
- Cache warming
- Any startup-time setup tasks