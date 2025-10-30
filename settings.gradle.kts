pluginManagement {
    val kotlinVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "otkotlin-prj"
//includeBuild("lessons")
includeBuild("my-kotlin-project")
includeBuild("my-kotlin-libs")
includeBuild("my-kotlin-utils")
