plugins {
    kotlin("jvm") version "2.0.21"
    id ("io.qameta.allure") version "2.8.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
tasks.wrapper {
    distributionType = Wrapper.DistributionType.ALL
    gradleVersion = "7.2"
}
tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation(kotlin("test"))
    /* Logging API and Impl*/
    implementation ("org.slf4j:slf4j-api:1.7.30")
    runtimeOnly ("org.slf4j:slf4j-simple:1.7.30")
    /* Retrofit Lib and Http Client Implementation */
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("io.qameta.allure:allure-okhttp3:2.13.9")
    /* Retrofit Convertors and Interceptors*/
    implementation ("com.squareup.retrofit2:converter-jackson:2.9.0")
    implementation ("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.1")
    /* Kotest */
    testImplementation ("io.kotest:kotest-runner-junit5:5.9.1")
    testImplementation ("io.kotest:kotest-assertions-core:5.9.1")
    testImplementation ("io.kotest:kotest-property:5.9.1")
    /* Kotest Extensions */
    testImplementation ("ru.iopump.kotest:kotest-allure:1.0.1")
    testImplementation("io.kotest.extensions:kotest-extensions-allure:1.4.0")
    /* Faker */
    implementation("io.github.serpro69:kotlin-faker:1.11.0")
}

kotlin {
    jvmToolchain(17)
}
allure {
    version = "2.13.9"
    aspectjVersion = "1.9.6"
    autoconfigure = false
    aspectjweaver = true
    resultsDir = file("$rootDir/allure-results")
}