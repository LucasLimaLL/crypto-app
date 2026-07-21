plugins {
    alias(libs.plugins.android.application)
    checkstyle
    jacoco
}

android {
    namespace = "br.com.lucaslima.cryptogram"
    compileSdk = 35

    defaultConfig {
        applicationId = "br.com.lucaslima.cryptogram"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            isDebuggable = true
            enableUnitTestCoverage = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        viewBinding = true
    }
}

checkstyle {
    toolVersion = "10.21.4"
    configFile = file("${rootDir}/config/checkstyle/checkstyle.xml")
    maxWarnings = 0
}

tasks.register<Checkstyle>("checkstyleMain") {
    source("src/main/java")
    include("**/*.java")
    classpath = files()
}

tasks.register<Checkstyle>("checkstyleTest") {
    source("src/test/java")
    include("**/*.java")
    classpath = files()
}

tasks.register<Checkstyle>("checkstyleAndroidTest") {
    source("src/androidTest/java")
    include("**/*.java")
    classpath = files()
}

tasks.named("check") {
    dependsOn("checkstyleMain", "checkstyleTest", "checkstyleAndroidTest")
}

jacoco {
    toolVersion = "0.8.12"
}

tasks.register<JacocoReport>("jacocoDebugTestReport") {
    dependsOn("testDebugUnitTest")
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
    sourceDirectories.setFrom(files("src/main/java"))
    classDirectories.setFrom(
        fileTree("build/intermediates/javac/debug/compileDebugJavaWithJavac/classes") {
            exclude(
                "**/R.class", "**/R$*.class", "**/BuildConfig.*",
                "**/Manifest*.*", "**/*Test*.*", "android/**/*.*"
            )
        }
    )
    executionData.setFrom(
        fileTree(layout.buildDirectory) {
            include("outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec")
        }
    )
}

tasks.register<JacocoCoverageVerification>("jacocoDebugTestCoverageVerification") {
    dependsOn("jacocoDebugTestReport")
    sourceDirectories.setFrom(files("src/main/java"))
    classDirectories.setFrom(
        fileTree("build/intermediates/javac/debug/compileDebugJavaWithJavac/classes") {
            exclude(
                "**/R.class", "**/R$*.class", "**/BuildConfig.*",
                "**/Manifest*.*", "**/*Test*.*", "android/**/*.*"
            )
        }
    )
    executionData.setFrom(
        fileTree(layout.buildDirectory) {
            include("outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec")
        }
    )
    violationRules {
        rule {
            limit { counter = "INSTRUCTION"; value = "COVEREDRATIO"; minimum = "0.97".toBigDecimal() }
            limit { counter = "LINE";        value = "COVEREDRATIO"; minimum = "0.97".toBigDecimal() }
            limit { counter = "BRANCH";      value = "COVEREDRATIO"; minimum = "0.90".toBigDecimal() }
            limit { counter = "METHOD";      value = "COVEREDRATIO"; minimum = "0.97".toBigDecimal() }
            limit { counter = "CLASS";       value = "COVEREDRATIO"; minimum = "0.97".toBigDecimal() }
        }
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)
    implementation(libs.activity)
    implementation(libs.fragment)
    implementation(libs.recyclerview)

    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.core.testing)

    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.espresso.core)
}
