# OtusLesson Project

This project demonstrates a Maven-based Java application with a focus on **Selenium** automation, **JUnit**, and
various other dependencies including **Guice**, **Guava**, **AssertJ**, and **JSoup**.

## Prerequisites

Before running the project, ensure you have the following installed:

- **Java 21** or higher (check by running `java -v`)
- **Maven 3.6+** (check by running `mvn -v`)

## Clone the Repository

To clone the repository, run the following command:

```bash
  git clone https://github.com/RazMKhitaryan/OtusLesson.git
cd OtusLesson
```

## Code Quality Checks

`mvn checkstyle:check`
`mvn spotbugs:check`

## Run the tests with command from terminal

`mvn clean install -Dbrowser=chrome -DbaseUrl=https://otus.ru -Dmode=remote -Durl=http://45.132.17.22/wd/hub -DthreadCount=3`
