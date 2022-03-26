val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "ani.saikou"
version = "0.0.1"
application {
    mainClass.set("ani.saikou.ApplicationKt")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }

    // to enable autocompletion for parsers/jars
    flatDir {
        dir("jars")
    }
}

tasks{
    shadowJar
}

dependencies {
    compileOnly(fileTree("jars"))

    // enable this if you wanna gradlew run and test, shadow jar task uses classes from runtime so they will get bundled
    runtimeOnly(fileTree("jars"))

    // JSON response
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-jackson:$ktor_version")

    // CORS plugin
    implementation("io.ktor:ktor-server-cors:$ktor_version")

    // Parsing libs
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("org.jsoup:jsoup:1.14.3")

    // Core KTOR server libs
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
//    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
//    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
