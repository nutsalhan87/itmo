plugins {
    id("java")
    war
}

group = "ru.mihan"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("jakarta.platform:jakarta.jakartaee-api:10.0.0")
    implementation("jakarta.jws:jakarta.jws-api:3.0.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")
    implementation(project(":godbless-shared"))

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}