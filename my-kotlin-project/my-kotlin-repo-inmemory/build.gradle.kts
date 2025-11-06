plugins {
    id("build-kmp")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.myKotlinCommon)
                api(projects.myKotlinRepoCommon)

                implementation(libs.coroutines.core)
                implementation(libs.db.cache4k)
                implementation(libs.uuid)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(projects.myKotlinRepoTests)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}
