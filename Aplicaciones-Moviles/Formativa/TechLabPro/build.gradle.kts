buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.10")
    }
}

plugins {
    // kotlin("jvm") version "2.4.0"
    application
}

apply(plugin = "org.jetbrains.kotlin.jvm")

group = "com.duoc.dsy1105"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    //implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    // lo dejo con 1.9 me dio errores con el 1.8.1, al final me rendi
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
}

application { mainClass.set("MainKt") }



