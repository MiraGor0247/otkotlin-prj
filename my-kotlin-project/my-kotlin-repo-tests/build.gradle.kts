plugins {
    id("build-kmp")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(kotlin("test-common"))
                api(kotlin("test-annotations-common"))

                api(libs.coroutines.core)
                api(libs.coroutines.test)
                implementation(projects.myKotlinCommon)
                implementation(projects.myKotlinRepoCommon)
            }
        }
        commonTest {
            dependencies {
                implementation(projects.myKotlinStubs)
            }
        }
        jvmMain {
            dependencies {
                api(kotlin("test-junit"))
            }
        }
    }
}
