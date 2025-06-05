plugins {
    kotlin("jvm") version "2.0.0"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}

sourceSets {
    main {
        kotlin.srcDir("waterBottles")
    }
}

application {
    mainClass.set("waterBottles.GameKt")
}
