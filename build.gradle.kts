plugins {
    alias(libs.plugins.android.application) apply false
    id("org.sonarqube") version "6.2.0.5505"
}

sonarqube {
    properties {
        property("sonar.projectKey", "criptograma-app")
        property("sonar.projectName", "Criptograma App")
        property("sonar.host.url", System.getenv("SONAR_HOST_URL") ?: "http://localhost:9000")
        property("sonar.java.coveragePlugin", "jacoco")
        property("sonar.coverage.jacoco.xmlReportPaths", "app/build/reports/jacoco/jacocoDebugTestReport/jacocoDebugTestReport.xml")
        property("sonar.qualitygate.wait", "true")
        property("sonar.sources", "app/src/main/java")
        property("sonar.tests", "app/src/test/java")
        property("sonar.java.source", "21")
    }
}
