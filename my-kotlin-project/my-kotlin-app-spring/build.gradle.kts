plugins {
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependencies)
    alias(libs.plugins.spring.kotlin)
    alias(libs.plugins.kotlinx.serialization)
    id("build-jvm")
}

dependencies {
    implementation(libs.spring.actuator)
    implementation(libs.spring.webflux)
    implementation(libs.spring.webflux.ui)
    implementation(libs.jackson.kotlin)
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib"))

    implementation(libs.coroutines.core)
    implementation(libs.coroutines.reactor)
    implementation(libs.coroutines.reactive)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)

    // Внутренние модели
    implementation(project(":my-kotlin-common"))
    implementation(project(":my-kotlin-app-common"))
    // api
    implementation(project(":my-kotlin-api-v1-jackson"))
    implementation(project(":my-kotlin-api-v1-mappers"))
    implementation("ru.otus.otuskotlin.mykotlin.libs:lib-logging-common")
    implementation("ru.otus.otuskotlin.mykotlin.libs:lib-logging-logback")
    // biz
    implementation(project(":my-kotlin-business"))
    // tests
    testImplementation(kotlin("test-junit5"))
    testImplementation(libs.spring.test)
    testImplementation(libs.mockito.kotlin)
}

tasks {
    withType<ProcessResources> {
        val vfile = rootProject.ext["spec-v1"]
        from(vfile) {
            into("/static")
            filter {
                // Устанавливаем версию в сваггере
                it.replace("\${VERSION_APP}", project.version.toString())
            }

        }
    }
}

tasks.test {
    useJUnitPlatform()
}
