rootProject.name = "my-kotlin-project"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

pluginManagement {
    includeBuild("../build-plugin")
    plugins {
        id("build-jvm") apply false
        id("build-kmp") apply false
        id("build-docker") apply false
    }
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

//plugins {
//    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
//}

// Включает вот такую конструкцию
//implementation(projects.m2l5Gradle.sub1.ssub1)
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":my-kotlin-tmp")
include(":my-kotlin-api-v1-jackson")
include(":my-kotlin-api-v1-mappers")
include(":my-kotlin-api-kmp")
include(":my-kotlin-app-common")
include(":my-kotlin-common")
include(":my-kotlin-api-log")
include(":my-kotlin-stubs")
include(":my-kotlin-business")
include(":my-kotlin-app-spring")
include(":my-kotlin-rabbit-mq")
include(":my-kotlin-repo-common")
include(":my-kotlin-repo-stubs")
include(":my-kotlin-repo-tests")
include(":my-kotlin-repo-inmemory")
include(":my-kotlin-repo-pg")

