import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "ru.mpei"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation("io.insert-koin:koin-core:3.4.0")
                implementation("io.insert-koin:koin-compose:1.0.0")
                implementation("org.bouncycastle:bcprov-jdk18on:1.72")
                implementation("org.bouncycastle:bcpkix-jdk18on:1.72")
                implementation("org.bouncycastle:bcutil-jdk18on:1.72")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation("io.insert-koin:koin-test-junit4:3.4.0")
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Lab1"
            packageVersion = "1.0.0"
        }
    }
}
