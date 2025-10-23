import java.nio.file.Files

plugins {
    alias(libs.plugins.kotlin.jvm)
    application
    id("com.gradleup.shadow") version "9.1.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.guava)
    implementation("ch.qos.logback:logback-classic:1.5.18")
    implementation("info.picocli:picocli:4.7.7")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.rometools:rome:2.1.0")
    implementation("me.tongfei:progressbar:0.10.0")
    implementation("com.github.seratch:notion-sdk-jvm-core:1.11.1")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    applicationName = "bronze"
    mainClass.set("com.howie.bronze.AppKt")
}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use Kotlin Test test framework
            useKotlinTest("2.2.0")
        }
    }
}

tasks.register("install") {
    dependsOn("shadowJar")
    doLast {
        val projectName = rootProject.name
        val buildDir = layout.buildDirectory.get().asFile
        val userhome = System.getProperty("user.home")
        val sourceDir = File("$buildDir/install/${projectName}-shadow/")
        val targetDir = File("$userhome/.$projectName/")

        logger.info("Source Dir: $sourceDir")
        logger.info("Target Dir: $targetDir")
        if (targetDir.exists()) {
            logger.info("Target dir is exists, delete it: $targetDir")
            targetDir.deleteRecursively()
        }

        logger.info("Copy source to target: $sourceDir -> $targetDir")
        sourceDir.copyRecursively(targetDir, true)


        val linkFrom = File("${targetDir.path}/bin/$projectName")
        linkFrom.setExecutable(true)

        val linkTarget = File("$userhome/bin/sparrow-cli")
        if (!linkTarget.exists()) {
            logger.info("Link target is not exists, create it: $linkTarget")
            Files.createSymbolicLink(linkTarget.toPath(), linkFrom.toPath())
        }

        val linkTargetShort = File("$userhome/bin/sw")
        if (!linkTargetShort.exists()) {
            logger.info("Link target is not exists, create it: $linkTargetShort")
            Files.createSymbolicLink(linkTargetShort.toPath(), linkFrom.toPath())
        }
    }
}