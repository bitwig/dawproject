job("Build and run tests") {
    container(displayName = "Gradle build", image = "openjdk:15") {
        kotlinScript { api ->
            // here goes complex logic
            api.gradlew("build")
        }
    }
}