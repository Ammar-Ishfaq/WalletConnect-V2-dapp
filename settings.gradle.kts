pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://jitpack.io")
//        maven { url 'https://jitpack.io' } // add like this
        jcenter() // Warning: this repository is going to shut down soon


    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        jcenter() // Warning: this repository is going to shut down soon


    }
}

rootProject.name = "WalletconnectIntegrationTestingV2"
include(":app")
include(":WC2Lib:common")
include(":WC2Lib:dapp")
