import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

buildscript {
    repositories {
        google()
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }

    dependencies {
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.gradle)
        classpath(libs.dokka.gradle.plugin)
        classpath(libs.publish.plugin)
    }
}

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.detekt)
    `maven-publish`
    signing
    alias(libs.plugins.dokka)
    alias(libs.plugins.skie)
    alias(libs.plugins.vanniktech.maven.publish)
    alias(libs.plugins.github.nexus.publish)
}


allprojects {
    repositories {
        mavenCentral()
        google()
    }
}

group = "ch.dreipol"


kotlin {
    jvmToolchain(libs.versions.jvm.toolchain.get().toInt())

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(libs.versions.jvm.target.get()))
        }
        publishAllLibraryVariants()
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework()
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.coroutines.core)
            api(libs.redux.kotlin.threadsafe)
            api(libs.redux.kotlin.thunk)
            api(libs.kermit)
            implementation(libs.kotlinx.serialization.json)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
            implementation(libs.kotlin.stdlib)
            implementation(libs.appcompat)
            implementation(libs.review)
            implementation(libs.review.ktx)
        }

        iosMain.dependencies {
            api(libs.kermit.simple)
        }
    }

    //    TODO: Remove as soon as Android Studio (Iguana) is fixed
    task("testClasses")
}

dependencies {
    detektPlugins(libs.detekt.formatting)
}

android {
    namespace = "ch.dreipol.dreimultiplatform"

    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    buildTypes {
        getByName("debug") {
            matchingFallbacks += listOf("release")
        }
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}


// workaround for https://youtrack.jetbrains.com/issue/KT-27170
configurations.create("compileClasspath")

detekt {
    config.setFrom("detekt.yml")
    source.setFrom("src")
    autoCorrect = true
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    exclude("**/generated/**") // but exclude our legacy internal package
}

tasks.register<Jar>("dokkaJavadocCommonJar") {
    dependsOn("dokkaHtml")
    group = "publishing"

    from("$buildDir/javadoc/common")
    archiveClassifier.set("javadoc")
}

mavenPublishing {
    configure(KotlinMultiplatform(javadocJar = JavadocJar.Dokka("dokkaJavadocCommonJar")))
    publishToMavenCentral()
    signAllPublications()
}

publishing {
    publications.withType<MavenPublication> {
        pom {
            name.set("dreimultiplatform")
            description.set("Shared Repository for KMM projects")
            url.set("https://github.com/dreipol/dreimultiplatform")
            scm {
                url.set("https://github.com/dreipol/dreimultiplatform")
                connection.set("scm:https://github.com/dreipol/dreimultiplatform.git")
                developerConnection.set("scm:git://github.com/dreipol/dreimultiplatform.git")
            }
            licenses {
                license {
                    name.set("The MIT License")
                    url.set("https://opensource.org/licenses/MIT")
                    distribution.set("repo")
                }
            }
            developers {
                developer {
                    id.set("melbic")
                    name.set("Samuel Bichsel")
                    email.set("samuel.bichsel@dreipol.ch")
                }
                developer {
                    id.set("kaiwidmer")
                    name.set("Kai Widmer")
                    email.set("kai.widmer@dreipol.ch")
                }
                developer {
                    id.set("tschuls")
                    name.set("Julia Strasser")
                    email.set("julia.strasser@dreipol.ch")
                }
                developer {
                    id.set("lailabecker")
                    name.set("Laila Becker")
                    email.set("laila.becker@dreipol.ch")
                }
            }
        }
    }
}

signing {
    val signingKey = System.getenv("PGP_KEY")
    var signingPassword = ""
    if (project.hasProperty("signing.password")) {
        signingPassword = project.property("signing.password").toString()
    }
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications)
}