plugins {
    kotlin("jvm") version "1.9.21"
    application
}

group = "uk.jaconet"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("MainKt")
}