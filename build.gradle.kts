import java.util.Locale

plugins {
    id ("java")
    id("io.papermc.paperweight.userdev") version "1.5.3"
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    paperDevBundle("1.17.1-R0.1-SNAPSHOT") // 원하는 Paper 버전
    implementation(fileTree("libs") { include("*.jar") })
}

extra.apply {
    set("pluginName", project.name.split("-").joinToString("") {
        it.replaceFirstChar {char ->
            if (char.isLowerCase()) char.titlecase(Locale.getDefault()) else char.toString()
        }
    })
    set("packageName", (project.name.replace("-", "")).lowercase())
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks {
   processResources {
        filesMatching("*.yml") {
            expand(project.properties)
            expand(extra.properties)
        }
    }
}