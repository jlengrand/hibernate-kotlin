import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.plugin.noarg") version "1.6.10"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.6.10"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.6.10"
    kotlin("jvm") version "1.6.10"
    application
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

group = "me.jlengrand"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.hibernate:hibernate-core:5.6.5.Final")
    testImplementation("org.hibernate:hibernate-testing:5.6.5.Final")

    implementation("com.h2database:h2:2.1.210")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("MainKt")
}