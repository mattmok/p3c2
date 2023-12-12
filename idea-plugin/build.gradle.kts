import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
}

buildscript {
//    repositories {
//        mavenLocal()
//        maven {
//            url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
//        }
//        gradlePluginPortal()
//        mavenCentral()
//    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.21")
        classpath("org.jetbrains.intellij.plugins:gradle-intellij-plugin:1.14.2")
    }
}

//val ideaVersion = property("idea_version") as String
//
//val yearVersion = ideaVersion
//    .split(".")
//    .first()
//    .substring(2)
//    .toInt()
//
//val noVersion = ideaVersion
//    .substring(ideaVersion.indexOf(".") + 1)
//    .toInt()
//
//val myPlugins = when (yearVersion) {
//    in 23..Int.MAX_VALUE -> setOf("vcs-git", "java")
//    22 -> if (noVersion == 3) setOf("vcs-git", "java") else setOf("git4idea", "java")
//    in 19..21 -> setOf("git4idea", "java")
//    else -> setOf("git4idea")
//}
//
//ext["ideaVersion"] = ideaVersion
//ext["yearVersion"] = yearVersion
//ext["noVersion"] = noVersion
//ext["myPlugins"] = myPlugins

subprojects {

    group = "com.alibaba.p3c.idea"

    apply(plugin = "java")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }

    java.sourceCompatibility = JavaVersion.VERSION_17
    java.targetCompatibility = JavaVersion.VERSION_17

    tasks.compileJava {
        options.encoding = "UTF-8"
    }

    configurations.all {
        resolutionStrategy {
            cacheChangingModulesFor(0, TimeUnit.SECONDS)
        }
    }

    repositories {
        mavenLocal()
        maven {
            isAllowInsecureProtocol = true
            url = uri("http://cloud.tiamaes.com:6001/nexus/repository/public/")
            credentials {
                findProperty("ossrhUsername")?.let {
                    username = it as String
                }
                findProperty("ossrhPassword")?.let {
                    password = it as String
                }
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        testImplementation("junit:junit:4.13.2")
    }
}