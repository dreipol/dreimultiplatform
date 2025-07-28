import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.vanniktech.maven.publish)
}

mavenPublishing {
    configure(KotlinMultiplatform(javadocJar = JavadocJar.Dokka("dokkaHtml")))
    publishToMavenCentral(SonatypeHost("https://ossrh-staging-api.central.sonatype.com/service/local/"))
    signAllPublications()

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

    signing {
        val signingKey = System.getenv("PGP_KEY")
        var signingPassword = ""
        if (project.hasProperty("signing.password")) {
            signingPassword = project.property("signing.password").toString()
        }
        useInMemoryPgpKeys(signingKey, signingPassword)
        sign(publishing.publications)
    }
}