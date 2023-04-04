import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("kapt")
    kotlin("plugin.jpa")
}

dependencies {
    val retrofitVersion = "2.9.0"
    val okHttpVersion = "4.10.0"
    val coroutinesVersion = "1.6.0-RC"

    implementation(project(":common"))

    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-jackson:$retrofitVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:$okHttpVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$coroutinesVersion")
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

tasks.withType<Jar> {
    enabled = true
}

tasks.withType<BootJar> {
    enabled = false
}
