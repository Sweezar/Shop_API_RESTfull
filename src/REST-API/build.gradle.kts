plugins {
    id("java")
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.liquibase.gradle") version "2.1.1"
}

group = "org.berneick"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation ("org.springframework.boot:spring-boot-starter-validation")

    // Swagger
//    implementation ("io.springfox:springfox-swagger2:3.0.0")
//    implementation ("io.springfox:springfox-swagger-ui:3.0.0")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")

    // Postgres
    runtimeOnly("org.postgresql:postgresql:42.7.4")

    // Liquibase
    implementation("org.liquibase:liquibase-core")

    // Jakarta Servlet (для Spring Boot 3.x)
    implementation("jakarta.servlet:jakarta.servlet-api:6.0.0")

    // Tests
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    enabled = true
    archiveClassifier.set("")
    manifest {
        attributes["Main-Class"] = "org.berneick.ShopApp"
    }
}