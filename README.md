# Spring Workshop: Dependency Inversion Principle, Lightweight Containers, and Dependency Injection
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