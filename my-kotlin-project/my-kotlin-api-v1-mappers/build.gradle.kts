plugins {
    id("build-jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(projects.myKotlinApiV1Jackson)
    implementation(projects.myKotlinCommon)

    testImplementation(kotlin("test-junit"))
    testImplementation(projects.myKotlinStubs)
}
