//build.gradle (프로젝트 수준): 앱 제작 환경 설정 파일, 기본 환경 설정 작업, 작업 환경 변경 시 수정
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.google.services) apply false
}