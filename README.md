# Components and connectors
# Part 1 - Spring Workshop: Dependency Inversion Principle, Lightweight Containers, and Dependency Injection
## Description

This project implements a text analysis application using Spring Framework to illustrate the concepts of:

    * Dependency Inversion Principle (DIP).
    * Lightweight Containers (IoC containers).
    * Dependency Injection (DI) with annotations.

The system consists of a GrammarChecker that depends on a SpellChecker. Spring is responsible for injecting the desired checker (English or Spanish) at runtime without the need to modify the GrammarChecker logic.

## Project Structure

```plaintext
src/
 └── main/
     ├── java/
     │   └── edu/eci/arsw/springdemo/
     │       ├── SpellChecker.java          # Interface
     │       ├── EnglishSpellChecker.java   # English implementation
     │       ├── SpanishSpellChecker.java   # Spanish implementation
     │       ├── GrammarChecker.java        # Main Bean that depends on SpellChecker
     │       └── ui/
     │           └── Main.java              # Class with main method
     │
     └── resources/
         └── applicationContext.xml         # Spring configuration

```

## Spring Configuration

The applicationContext.xml file is located in src/main/resources and contains:

    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:context="http://www.springframework.org/schema/context"
        xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.2.xsd
            http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.2.xsd">

        <!-- Automatically scan Beans in the base package -->
        <context:component-scan base-package="edu.eci.arsw" />
    </beans>

This allows Spring to detect classes annotated with @Service and handle injection automatically.

## Execution

1. Requirements

    Java 8+
    Maven 3+
    NetBeans or any IDE compatible with Maven

2. Clone the project

    git clone https://github.com/Enigmus12/SpringBoot_REST_API_Blueprints-03.git
    cd Parte1

3. Compile

    mvn clean install

4. Run

    mvn exec:java


5. Expected output (with EnglishSpellChecker)

    Spell checking output: Checked with english checker: la la la Plagiarism checking output: Not available yet


💡 Switch from English to Spanish

In GrammarChecker.java, change the annotation:

    @Autowired
    @Qualifier("englishSpellChecker")  // <-- change to "spanishSpellChecker"
    private SpellChecker sc;


6. Output with SpanishSpellChecker:

    Spell checking output: revisando (la la la ) con el verificador de sintaxis del espanol Plagiarism checking output: Not available yet

## Workshop Solution 1
# Part I: Basic Exercise

1. Define the interface and dependencies

    SpellChecker is the abstraction.
    EnglishSpellChecker and SpanishSpellChecker are implementations.
    GrammarChecker depends on SpellChecker.

2. Configure Spring with annotations

    @Service on each SpellChecker implementation.
    @Service on GrammarChecker and @Autowired for the dependency.
    @Qualifier to resolve ambiguity between the two implementations.

3. Create the lightweight Spring container (IoC Container)

    applicationContext.xml with context:component-scan to automatically detect beans.

4. Dependency injection at runtime

    Spring injects EnglishSpellChecker or SpanishSpellChecker into GrammarChecker as specified in the @Qualifier.

5. Run and validate

    Main.java creates the context, retrieves GrammarChecker, and invokes check.

## Final Result

It is possible to dynamically switch between different spell checkers without modifying the GrammarChecker logic, fulfilling:

    Dependency Inversion Principle (DIP): GrammarChecker depends on an interface (SpellChecker) and not on concrete implementations.
    Lightweight container (IoC): Spring handles object creation.
    Dependency Injection (DI): SpellChecker is automatically injected into GrammarChecker.

# Part 2 - Dependency Injection, Persistence, and Filtering

## Description

This project implements a blueprint management system using Spring Framework, applying the principles of:

    * Dependency Inversion Principle (DIP).
    * Lightweight Containers (IoC containers).
    * Dependency Injection (DI) with annotations.

The system manages blueprints (sets of points in a plane) with persistence in memory and filtering capabilities. Two filters can be applied to the blueprints before returning them:

    (A) Redundancy Filter: removes consecutive duplicate points.
    (B) Subsampling Filter: keeps one of every two points (even indices).

By switching the @Primary annotation between the filters, the system dynamically changes the filtering behavior without modifying service logic.

## Project Structure

```plaintext

src/
 └── main/
     ├── java/
     │   └── edu/eci/arsw/blueprints/
     │       ├── model/
     │       │   ├── Point.java               # Represents a point in 2D plane
     │       │   └── Blueprint.java           # Represents a blueprint (list of points)
     │       │
     │       ├── persistence/
     │       │   ├── BlueprintsPersistence.java      # Persistence interface
     │       │   ├── BlueprintNotFoundException.java # Exception for missing blueprint
     │       │   ├── BlueprintPersistenceException.java # Exception for persistence issues
     │       │   └── impl/
     │       │       ├── InMemoryBlueprintPersistence.java # In-memory persistence implementation
     │       │       └── Tuple.java                   # Utility class for map keys
     │       │
     │       ├── services/
     │       │   ├── BlueprintsServices.java          # Service layer with DI
     │       │   └── filters/
     │       │       ├── BlueprintFilter.java         # Filter abstraction
     │       │       ├── RedundancyFilter.java        # Removes duplicate consecutive points
     │       │       └── SubsamplingFilter.java       # Keeps even indexed points
     │       │
     │       └── App.java                      # Main program (test/demo)
     │
     └── test/
         └── java/
             └── edu/eci/arsw/blueprints/test/
                 ├── persistence/impl/
                 │   └── InMemoryPersistenceTest.java  # Tests persistence
                 └── services/filters/
                     ├── RedundancyFilterTest.java     # Tests redundancy filter
                     └── SubsamplingFilterTest.java    # Tests subsampling filter
```

## Spring Configuration

The application uses annotation-based configuration.

    @Repository for persistence (InMemoryBlueprintPersistence).
    @Service for the service layer (BlueprintsServices).
    @Component for filters (RedundancyFilter, SubsamplingFilter).
    @Primary to select the active filter implementation.

    @Component
    @Primary   // <-- Switch this annotation between filters
    public class RedundancyFilter implements BlueprintFilter { ... }
This allows Spring to inject the correct filter dynamically into BlueprintsServices.

## Execution

1. Requirements
    Java 8+
    Maven 3+
    IDE (IntelliJ, Eclipse, NetBeans)

2. Clone the project
    git clone https://github.com/Enigmus12/SpringBoot_REST_API_Blueprints-03.git
    cd Parte2

3. Compile
    mvn clean install

4. Run
    mvn exec:java

5. Example Output

With RedundancyFilter as @Primary:

```plaintext
    All (filtered): 
    - null/_bpname_ points: 2
    (140,140) (115,115)
    - null/plano1 points: 4
    (0,0) (1,1) (2,2) (3,3)
    - null/bp2 points: 4
    (10,10) (20,20) (30,30) (40,40)
    Blueprints by juan (filtered): 1
    Blueprint 'juan/plano1' points (filtered): 4
```

## Tests

    * InMemoryPersistenceTest: verifies persistence (save, retrieve, duplicates).
    * RedundancyFilterTest: checks that consecutive duplicate points are removed.
    * SubsamplingFilterTest: checks that only points at even indices are kept.

mvn test

## Workshop Steps & Solution

1. Configure Dependency Injection

    Added Spring dependencies in pom.xml.
    Annotated persistence, service, and filter classes with @Repository, @Service, @Component.

2. Implement Persistence

    Added InMemoryBlueprintPersistence with save, get, and getByAuthor.
    Validated with JUnit tests.

3. Create Application with Spring

    App.java creates context and retrieves BlueprintsServices via DI.

4. Implement Filtering

    Added abstraction BlueprintFilter.
    Implemented RedundancyFilter and SubsamplingFilter.
    Injected filter into BlueprintsServices.

5. Add Tests for Filters

    Verified both filters work independently.
    Switching @Primary annotation changes the active filter dynamically.


## Author 
    Juan David Rodriguez Rodriguez