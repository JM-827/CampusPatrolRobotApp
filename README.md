## Stage 1: 프로젝트 설정
- Firebase 프로젝트 생성 및 google-services.json 추가.
- Remote Config 설정 및 auth_key 값 확인.

## Stage 2: Firebase 및 Google Cloud 설정
- Firestore Database: 테스트 모드, 위치 서울(asia-northeast3) 설정.
- Firebase Functions: Blaze 요금제 업그레이드, firebase-tools 설치(npm install -g firebase-tools), 배포는 나중 단계.
- FCM: Google Cloud API 활성화로 코드 구현 예정(추후 단계), Firebase 콘솔창 직접 사용 안 함.
- Google Cloud API: Cloud Functions, Cloud Messaging, Firestore, Maps SDK for Android 활성화.
- 서비스 계정 생성: 이름(campus-patrol-service), 역할(Cloud Functions 관리자, Cloud Datastore 사용자).

## Stage 3: 초기 UI 구성
- MainActivity.kt에 초기 화면 구성 (Jetpack Compose 사용).
- 화면 추가를 위해 ChatActivity1, ChatActivity2, MapActivity 를 작성 하고 MainActivity.kt와 같은 공간에 배치.
- 작성한 파일들은 AndroidManifest.xml 파일에서 선언 해준다.
- \<activity\>는 여는 태그, \</activity\>는 닫는 태그이며, 내용이 없으면 \<activity /\>처럼 바로 닫는 형태로 작성 가능.
- pixel6로 애뮬레이터 테스트 완료.

## Stage 4: 세부 화면 UI 설정
- MainActivity : 다크 테마와 카드 스타일 네비게이션 버튼
- ChatActivity1: "귀환", "잠시 대기", "다시 작동시작" 버튼으로 명령어 채팅 UI 구현, 채팅 로그 영구 저장, "로그 지우기" 버튼과 다이얼로그 추가.
- ChatActivity2: 더미 데이터로 이벤트 알림 UI 구현, Swipe Up 기능 준비 (다음 단계에서 세부 구현)
- MapActivity: 실시간 로봇 위치 확인을 위한 페이지 (다음 단계에서 세부 구현)

## CampusPatrolRobotApp - 파일 역할 요약
- MainActivity.kt: 앱의 "두뇌" 역할 파일, 앱이 할 일을 적는 곳, 인터넷에서 "안전 확인 코드" 가져오기 작업, 다크 테마와 카드 스타일 네비게이션 버튼으로 ChatActivity1, ChatActivity2, MapActivity로 이동 가능, 새로운 기능 추가 시 수정(예: 버튼 추가)
- ChatActivity1.kt: 명령어 채팅 UI 구현 파일, "귀환", "잠시 대기", "다시 작동시작" 버튼으로 로봇 명령 전송, 채팅 로그를 SharedPreferences로 영구 저장, "로그 지우기" 버튼과 다이얼로그로 로그 관리, LLM 연결 예정
- ChatActivity2.kt: 이벤트 알림 UI 구현 파일, 더미 데이터로 이벤트 표시, Swipe Up 기능 준비(Stage 9에서 세부 구현), 이벤트 관련 UI 확장 시 수정
- MapActivity.kt: 실시간 로봇 위치 확인 UI 준비 파일, 현재는 기본 텍스트 표시, Stage 5에서 지도 기능 구현 예정, 지도 관련 기능 추가 시 수정
- build.gradle (앱 수준): 앱이 필요로 하는 도구 목록 파일, Firebase 사용을 위해 도구 추가, 새로운 도구 필요 시 수정
- build.gradle (프로젝트 수준): 앱 제작 환경 설정 파일, 기본 환경 설정 작업, 작업 환경 변경 시 수정
- libs.versions.toml: 도구 버전 정리 파일, 버전 정리로 문제 방지, 도구 버전 변경이나 추가 시 수정
- XML 파일(로우 레벨): 앱 화면 디자인 파일, 앱 아이콘이나 배경 파일 확인, 현재는 Jetpack Compose 사용으로 제한적 활용, 디자인 변경 시 수정(예: 버튼 추가)
- AndroidManifest.xml(하이 레벨 XML): 앱 구성 요소 선언 파일, ChatActivity1, ChatActivity2, MapActivity 선언, 새로운 Activity 추가 시 수정
- settings.gradle.kts: 앱 프로젝트 구조 정리 파일, 구조 설정으로 앱 작동 지원, 구조 변경 시 수정(예: 새로운 기능 추가)
