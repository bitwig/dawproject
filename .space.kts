job("Build and run tests") {
    container(displayName = "Gradle build", image = "openjdk:15") {
        shellScript {
            content = """
                echo Hello
                echo World!
            """
        }
      kotlinScript { api ->
            // here goes complex logic
            api.gradlew("build")
        }
    }
}