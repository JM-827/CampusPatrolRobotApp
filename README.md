## Stage 1: 프로젝트 설정
- Firebase 프로젝트 생성 및 google-services.json 추가.
- Remote Config 설정 및 auth_key 값 확인.

CampusPatrolRobotApp - 파일 역할 요약

- MainActivity.kt: 앱의 "두뇌" 역할 파일, 앱이 할 일을 적는 곳, 인터넷에서 "안전 확인 코드" 가져오기 작업, 새로운 기능 추가 시 수정(예: 버튼 추가)
- build.gradle (앱 수준): 앱이 필요로 하는 도구 목록 파일, Firebase 사용을 위해 도구 추가, 새로운 도구 필요 시 수정
- build.gradle (프로젝트 수준): 앱 제작 환경 설정 파일, 기본 환경 설정 작업, 작업 환경 변경 시 수정
- libs.versions.toml: 도구 버전 정리 파일, 버전 정리로 문제 방지, 도구 버전 변경이나 추가 시 수정
- XML 파일: 앱 화면 디자인 파일, 앱 아이콘이나 배경 파일 확인, 디자인 변경 시 수정(예: 버튼 추가)
- settings.gradle.kts: 앱 프로젝트 구조 정리 파일, 구조 설정으로 앱 작동 지원, 구조 변경 시 수정(예: 새로운 기능 추가)
