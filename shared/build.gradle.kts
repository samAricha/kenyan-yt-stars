plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization") version "1.9.0"
}

kotlin {
    androidTarget()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        val voyagerVersion = "1.0.0-rc07"
        val ktorVersion = "2.2.2"

        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                //additional dependencies
                implementation("media.kamel:kamel-image:0.8.2")
                implementation("io.ktor:ktor-client-core:2.3.5")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
                implementation("io.ktor:ktor-client-content-negotiation:2.3.5")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.5")
                //moko navigation
                api("dev.icerock.moko:mvvm-core:0.16.1")
                api("dev.icerock.moko:mvvm-compose:0.16.1")

                //voyager
                // Used for the basic navigation
                implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")

                // Allows us to use tab navigation for the bottom bar
                implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")

                // Support for transition animations
                implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")

                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.activity:activity-compose:1.7.2")
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.10.1")

                //additional dependencies
                implementation("io.ktor:ktor-client-android:2.3.5")
                //video-player
                implementation("androidx.media3:media3-exoplayer:1.1.1")
                implementation("androidx.media3:media3-exoplayer-dash:1.1.1")
                implementation("androidx.media3:media3-ui:1.1.1")
                //exo-player
                implementation("com.google.android.exoplayer:exoplayer:2.19.1")
                implementation ("androidx.media3:media3-exoplayer:1.1.1")
                implementation ("androidx.media3:media3-ui:1.1.1")
                implementation ("androidx.media3:media3-exoplayer-dash:1.1.1")
                //youtube-player
                implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:11.1.0")
                //logger
                implementation("io.ktor:ktor-client-logging:$ktorVersion")


            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                implementation("io.ktor:ktor-client-darwin:2.3.5")
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.myapplication.common"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}
dependencies {
    implementation("androidx.media3:media3-common:1.1.1")
}
