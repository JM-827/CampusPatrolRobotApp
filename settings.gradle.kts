//settings.gradle.kts: 앱 프로젝트 구조 정리 파일, 구조 설정으로 앱 작동 지원, 구조 변경 시 수정(예: 새로운 기능 추가)
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

rootProject.name = "CampusPatrolRobotApp"
include(":app")
