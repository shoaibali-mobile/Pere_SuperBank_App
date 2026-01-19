pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Pere-Super-App-Bank"
include(":app")
include(":features:home")
include(":features:profile")
include(":features:kyc")
include(":features:banking")
include(":features:payments")
include(":features:kyc:api")
include(":features:cards")
include(":core:auth")
include(":core:network")
include(":core:personalization")
include(":foundation:design")
include(":foundation:utils")
include(":core:auth:api")
include(":core:auth:impl")
