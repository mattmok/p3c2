plugins {
    id("org.jetbrains.intellij")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

intellij {
    type.set("IC")
    version.set("${property("ideaVersion")}")
    pluginName.set("${property("pluginName")}")
    plugins.set(listOf("vcs-git", "java"))
    updateSinceUntilBuild.set(false)
    jreRepository.set("https://intellij-repository.tiamaes.com/intellij-jbr")
}

version = "1.0.1.1"

ext["isReleaseVersion"] = !version.toString().endsWith("SNAPSHOT")

dependencies {
    implementation("org.freemarker:freemarker:2.3.31")
    implementation("com.alibaba.p3c:p3c-pmd:2.1.1.2")
    implementation("org.javassist:javassist:3.29.2-GA")
}

publishing {
    repositories {
        maven {
            url = if (!version.toString().toUpperCase().contains("SNAPSHOT")) {
                uri("http://cloud.tiamaes.com:6001/nexus/repository/m1-releases/")
            } else {
                uri("http://cloud.tiamaes.com:6001/nexus/repository/m1-snapshots/")
            }
            credentials {
                findProperty("ossrhUsername")?.let {
                    username = it as String
                }
                findProperty("ossrhPassword")?.let {
                    password = it as String
                }
            }
        }
    }

    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "p3c-common"
            from(components["java"])
            pom {
                name.set("p3c-common")
                description.set("P3c Idea Plugin Common.'")
                url.set("https://github.com/mattmok/p3c2")
            }
        }
    }
}
tasks.initializeIntelliJPlugin {
    offline.set(true)
}