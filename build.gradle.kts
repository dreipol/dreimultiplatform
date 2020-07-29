group = "ch.dreipol"
version = "0.0.1-SNAPSHOT"

plugins {
    `maven-publish`
    kotlin("jvm") version "1.3.71"
}

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))
}

publishing {
    publications {
        create<MavenPublication>("default") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/dreipol/dreimultiplatform")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
