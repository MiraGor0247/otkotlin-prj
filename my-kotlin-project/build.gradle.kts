plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    id("build-docker") apply false
}

group = "ru.otus.otuskotlin.mykotlin"
version = "0.0.1"

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    group = rootProject.group
    version = rootProject.version
}

ext {
    val specDir = layout.projectDirectory.dir("../specs")
    set("spec-v1", specDir.file("specs-op-v1.yaml").toString())
    set("spec-log", specDir.file("specs-op-log.yaml").toString())
}

tasks {
    register("build" ) {
        group = "build"
    }
    register("clean" ) {
        group = "build"
        subprojects.forEach { proj ->
            println("PROJ $proj")
            proj.getTasksByName("clean", false).also {
                this@register.dependsOn(it)
            }
        }
    }
    register("check" ) {
        group = "verification"
        subprojects.forEach { proj ->
            println("PROJ $proj")
            proj.getTasksByName("check", false).also {
                this@register.dependsOn(it)
            }
        }
    }

    register("buildImages") {
        dependsOn(project("my-mykotlin-app-spring").tasks.getByName("bootBuildImage"))
    }
}