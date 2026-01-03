import org.gradle.internal.os.OperatingSystem

plugins {
    id("java")
    id("xyz.wagyourtail.unimined") version ("1.4.1")
}

group = "com.github.awruff"
version = "1.0.0+1.8.9"

val lwjglVersion = "3.3.6"
val lwjglNatives = when (OperatingSystem.current()) {
    OperatingSystem.LINUX -> "natives-linux"
    OperatingSystem.WINDOWS -> "natives-windows"
    else -> throw GradleException("Unsupported OS: ${OperatingSystem.current()}")
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://maven.taumc.org/releases")
}

unimined.minecraft {
    version("1.8.9")

    mappings {
        calamus()
        feather(28)
    }

    ornitheFabric {
        loader("0.17.3")
    }

    runs.config("client") {
        javaVersion = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation("org.embeddedt.celeritas:celeritas-common:2.4.0-dev.3") {
        isTransitive = false
    }

    implementation("org.joml:joml:1.10.5")
    implementation("it.unimi.dsi:fastutil:8.5.15")

    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")

    "modImplementation"("com.github.moehreag:legacy-lwjgl3:efa53cb509")

    implementation(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))

    runtimeOnly("org.lwjgl:lwjgl::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-assimp::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-glfw::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-openal::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-opengl::$lwjglNatives")
    runtimeOnly("org.lwjgl:lwjgl-stb::$lwjglNatives")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

configurations.all {
    exclude(group = "org.lwjgl.lwjgl") // remove lwjgl2
}