plugins {
    id("build-kmp")
}

kotlin {
    sourceSets {
        all { languageSettings.optIn("kotlin.RequiresOptIn") }

        commonMain {
            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation(project(":my-kotlin-common"))
                implementation(project(":my-kotlin-stubs"))
                implementation(project(":my-kotlin-repo-tests"))

                implementation(libs.cor)
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                api(libs.coroutines.test)
                implementation(projects.myKotlinRepoTests)
                implementation(projects.myKotlinRepoInmemory)
            }
        }
        jvmMain {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        jvmTest {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}
