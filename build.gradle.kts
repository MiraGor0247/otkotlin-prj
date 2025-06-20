plugins {
    kotlin("jvm") version "2.0.10"
}

group = "ru.otus.otuskotlin.marketplace"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}