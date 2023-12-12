plugins {
    id("org.jetbrains.intellij")
}

intellij {
    type.set("IC")
    version.set("${property("ideaVersion")}")
    pluginName.set("${property("pluginName")}")
    plugins.set(listOf("vcs-git", "java"))
    updateSinceUntilBuild.set(false)
    jreRepository.set("https://intellij-repository.tiamaes.com/intellij-jbr")
}

tasks {
    patchPluginXml {
        sinceBuild.set("233")
        pluginDescription.set(
                """
<h1>English Readme：</h1>
<h2>The plugin conflicts with the official plugin. Please uninstall the original plugin before installing this plugin</h2>
<p>Alibaba Java Coding Guidelines plugin support.Fix some bug.such as <a href="https://github.com/alibaba/p3c/issues/898">issues-898</a>,<a href="https://github.com/alibaba/p3c/issues/900">issues-900</a></p>
<p>Official plugin URL: <a href="https://plugins.jetbrains.com/plugin/10046-alibaba-java-coding-guidelines">Alibaba Java Coding Guidelines</a></p>

<h1>中文说明：</h1>
<h2>插件与官方插件会冲突，请先卸载原插件，再安装本插件</h2>
<p>阿里巴巴Java编码规范检查插件。修复了一些官方一直未修复的Bug。如 <a href="https://github.com/alibaba/p3c/issues/898">issues-898</a>,<a href="https://github.com/alibaba/p3c/issues/900">issues-900</a></p>
<p>官方插件地址：<a href="https://plugins.jetbrains.com/plugin/10046-alibaba-java-coding-guidelines">Alibaba Java Coding Guidelines</a></p>

        """.trimIndent()
        )
        changeNotes.set(
                """
<ul>
1.0.1-2023.3
<li>Compatible with IntelliJ IDEA Community 2023.3</li>
<li>Upgrade kotlin-gradle-plugin to 1.9.21</li>
<li>Upgrade gradle-intellij-plugin to 1.14.2</li>
</ul>
        """.trimIndent()
        )
    }

    publishPlugin {
        token.set(findProperty("intellijPublishToken") as String)
    }

    signPlugin {
        certificateChainFile.set(file(providers.gradleProperty("intellijMarketSignerChain").get()))
        privateKeyFile.set(file(providers.gradleProperty("intellijMarketSignerPrivate").get()))
        password.set(providers.gradleProperty("intellijMarketSignerPrivatePassword").get())
    }

    initializeIntelliJPlugin {
        offline.set(true)
    }

    downloadZipSigner {
        cliPath.set(providers.gradleProperty("intellijMarketSignerPath"))
        cli.set(file(cliPath))
    }

    publish.configure {
        enabled = false
    }
}

dependencies {
    implementation("org.freemarker:freemarker:2.3.31")
    implementation(project(":p3c-common"))
    implementation("org.javassist:javassist:3.29.2-GA")
}