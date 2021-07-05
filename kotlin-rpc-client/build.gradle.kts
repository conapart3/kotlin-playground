import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
}

group 'com.conal'
version '1.0-SNAPSHOT'

repositories {
    mavenCentral()
}

dependencies {
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
    compile "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.4.10"
    compile "org.jetbrains.kotlin:kotlin-reflect:1.4.10"
    compile "com.squareup.moshi:moshi-kotlin:1.11.0"
    compile "com.squareup.moshi:moshi-adapters:1.11.0"
    compile "com.squareup.okhttp3:okhttp:4.9.0"
    testCompile "io.kotlintest:kotlintest:2.0.7"
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}