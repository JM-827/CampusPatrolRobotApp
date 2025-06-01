//앱의 "두뇌" 역할 파일, 앱이 할 일을 적는 곳, 새로운 기능 추가 시 수정(예: 버튼 추가)
package com.example.campuspatrolrobot

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.campuspatrolrobot.ui.theme.CampusPatrolRobotAppTheme
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) { //onCreate : 앱 화면이 처음 만들어질 때 호출되는 함수
        super.onCreate(savedInstanceState) //super은 부모 클래스를 가르키며, 기본 동작이 가능하도록 초기화 작업을 한다.
        enableEdgeToEdge() //앱이 전체 화면을 꽉 채우도록 만듬
        setContent { //Compose에서 UI를 설정하는 함수.
            CampusPatrolRobotAppTheme { //앱의 테마를 구성
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding -> //기본 레이아웃 구조 설정
                    Greeting(
                        name = "Campus Patrol Robot App", //Greeting 함수가 표시할 텍스트
                        modifier = Modifier.padding(innerPadding) //UI 스타일 조정, 패딩은 여백 조절
                    )
                }
            }
        }

        // Firebase Remote Config 초기화
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0 // 캐시 시간 설정
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(mapOf("auth_key" to "default_auth_key")) // 기본값 설정

        // Remote Config에서 auth_key 가져오기
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val authKey = remoteConfig.getString("auth_key")
                Log.d("RemoteConfig", "Auth Key: $authKey")
                // 필요 시 UI에 반영하거나 다른 로직에 사용
            } else {
                Log.e("RemoteConfig", "Fetch failed")
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Welcome to $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true) // false 면 배경색이 없어짐 (투명하거나, 기본값)
@Composable
fun GreetingPreview() {
    CampusPatrolRobotAppTheme {
        Greeting("Android")
    }
}