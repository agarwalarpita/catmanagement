plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("org.springframework.boot:spring-boot-starter-web:2.5.0")
    implementation ("org.springframework.boot:spring-boot-starter-security:2.5.3")
    testImplementation("org.projectlombok:lombok:1.18.26")
    compileOnly ("org.projectlombok:lombok:1.18.20")
    annotationProcessor ("org.projectlombok:lombok:1.18.20")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation ("org.mockito:mockito-core:3.12.4")
    testImplementation ("org.mockito:mockito-junit-jupiter:3.12.4")
    testImplementation ("org.springframework.boot:spring-boot-starter-test:2.5.4")
}

tasks.test {
    useJUnitPlatform()
}