import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.6.0"
    id("org.jetbrains.kotlin.plugin.noarg") version "1.5.31"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.5.31"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.5.31"
}

group = "com.conal"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}
dependencies {
    implementation(group = "com.fasterxml.jackson.core", name = "jackson-databind", version = "2.0.1")
    implementation(
        "com.fasterxml.jackson.module",
        "jackson-module-kotlin",
        "2.11.0"
    )// https://mvnrepository.com/artifact/javax.persistence/javax.persistence-api
    implementation("com.fasterxml.jackson.datatype", "jackson-datatype-jsr310", "2.11.0")
    implementation("javax.persistence", "javax.persistence-api", "2.1")
    testImplementation(kotlin("test-junit5"))
    implementation("co.paralleluniverse:quasar-core:0.8.0")
    // https://mvnrepository.com/artifact/cglib/cglib
    implementation(group = "cglib", name = "cglib", version = "2.2.2")
    // https://mvnrepository.com/artifact/org.hibernate/hibernate-gradle-plugin
    implementation(group = "org.hibernate", name = "hibernate-gradle-plugin", version = "5.6.2.Final")
    implementation(group = "org.hibernate", name = "hibernate-core", version = "5.6.2.Final")
//    implementation(group="org.hibernate", name="hibernate-entitymanager", version="5.6.2.Final")
// https://mvnrepository.com/artifact/com.h2database/h2
//    runtimeOnly(group="com.h2database", name="h2", version="1.3.148")
    runtimeOnly(group = "org.postgresql", name = "postgresql", version = "42.3.0")
    implementation("com.google.code.gson", "gson", "2.8.6")

    implementation("org.apache.kafka", "kafka-clients", "3.0.0")
    implementation(group = "org.liquibase", name = "liquibase-core", version = "4.16.1")
    implementation(group="org.postgresql", name="postgresql", version="42.5.0")


    // https://mvnrepository.com/artifact/org.apache.avro/avro
    implementation("org.apache.avro:avro:1.11.0")
//    implementation(group = "net.corda", name = "corda-avro-schema", version = "5.0.0.17-SNAPSHOT")
// https://mvnrepository.com/artifact/io.javalin/javalin
    implementation("io.javalin:javalin:4.3.0")
    implementation(kotlin("stdlib-jdk8"))

}
tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

apply(plugin = "kotlin-noarg")
apply(plugin = "kotlin-jpa")
apply(plugin = "kotlin-allopen")

// Making all persistence entity open and with an empty constructor to allow Hibernate to work.
//apply(plugin="kotlin-allopen")
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}