pluginManagement {
    plugins {
        id("org.jetbrains.intellij") version "1.16.1"
        id("org.jetbrains.changelog") version "1.3.1"
        id("org.jetbrains.kotlin.jvm") version "1.9.21"
    }

    repositories {
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositories {
        mavenLocal()
        maven {
            isAllowInsecureProtocol = true
            url = uri("http://cloud.tiamaes.com:6001/nexus/repository/public/")
            credentials {
                settings.extra.get("ossrhUsername")?.let {
                    username = it as String
                }
                settings.extra.get("ossrhPassword")?.let {
                    password = it as String
                }
            }
        }
        mavenCentral()
    }
}
include("p3c-idea")
include("p3c-common")
