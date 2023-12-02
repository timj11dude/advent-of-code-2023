plugins {
    kotlin("jvm") version "1.9.21"
}

group = "uk.jaconet"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("io.ktor:ktor-bom:2.3.6"))
    implementation("io.ktor:ktor-client-core")
}

kotlin {
    jvmToolchain(21)
}