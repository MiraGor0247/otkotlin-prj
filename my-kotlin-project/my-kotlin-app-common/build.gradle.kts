plugins {
    id("build-kmp")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    sourceSets {
        val coroutinesVersion: String by project
        commonMain {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation(libs.coroutines.core)
                implementation(libs.kotlinx.serialization.core)
                implementation(libs.kotlinx.serialization.json)

                // transport models
                implementation(project(":my-kotlin-common"))
                implementation(project(":my-kotlin-api-log"))
                implementation(project(":my-kotlin-api-kmp"))
                implementation(project(":my-kotlin-business"))
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                implementation(libs.coroutines.core)
                implementation(libs.coroutines.test)
            }
        }

        jvmTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        nativeTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}
