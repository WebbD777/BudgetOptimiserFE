import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    kotlin("plugin.serialization") version "2.0.20" // Match your Kotlin version if not using version catalogs
}

dependencies {
    implementation(project(":app:shared"))

    implementation(compose.desktop.currentOs)
    implementation(libs.kotlinx.coroutinesSwing)

    implementation(libs.compose.uiToolingPreview)
    // Ktor Client Core
    implementation("io.ktor:ktor-client-core:3.0.3") // Or your project's Ktor version
    // Ktor Client Engine (CIO engine is standard for desktop JVM)
    implementation("io.ktor:ktor-client-cio:3.0.3")
    // Content Negotiation for handling JSON
    implementation("io.ktor:ktor-client-content-negotiation:3.0.3")
    // Kotlinx Serialization JSON provider
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.0.3")
}

compose.desktop {
    application {
        mainClass = "co.za.dylanwebb.budgetoptimiserfe.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "co.za.dylanwebb.budgetoptimiserfe"
            packageVersion = "1.0.0"
        }
    }
}