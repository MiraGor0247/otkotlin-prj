plugins {
    id("build-jvm")
    application
    alias(libs.plugins.shadowJar)
    id("build-docker")
}

application {
    mainClass.set("ru.otus.otuskotlin.mykotlin.app.rabbit.ApplicationKt")
}

docker {
    imageName = "my-kotlin-rabbit"
}

dependencies {

    implementation(kotlin("stdlib"))

    implementation(libs.rabbitmq.client)
    implementation(libs.jackson.databind)
    implementation(libs.logback)
    implementation(libs.coroutines.core)

    implementation(project(":my-kotlin-common"))
    implementation(project(":my-kotlin-app-common"))
    implementation("ru.otus.otuskotlin.mykotlin.libs:lib-logging-logback")

    // v1 api
    implementation(project(":my-kotlin-api-v1-jackson"))
    implementation(project(":my-kotlin-api-v1-mappers"))

    implementation(project(":my-kotlin-business"))
    implementation(project(":my-kotlin-stubs"))

    testImplementation(libs.testcontainers.rabbitmq)
    testImplementation(kotlin("test"))
}

tasks {
    shadowJar {
        manifest {
            attributes(mapOf("Main-Class" to application.mainClass.get()))
        }
    }

    dockerBuild {
        dependsOn("shadowJar")
    }
}
