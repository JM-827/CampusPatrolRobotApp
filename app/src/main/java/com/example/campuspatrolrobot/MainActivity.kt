package com.example.campuspatrolrobot

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.campuspatrolrobot.ui.theme.CampusPatrolRobotAppTheme
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Firebase Remote Config 초기화
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(mapOf("auth_key" to "default_auth_key"))

        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val authKey = remoteConfig.getString("auth_key")
                Log.d("RemoteConfig", "Auth Key: $authKey")
            } else {
                Log.e("RemoteConfig", "Fetch failed")
            }
        }

        // 화면 구성
        setContent {
            CampusPatrolRobotAppTheme(darkTheme = true) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Campus Patrol Robot",
                            modifier = Modifier.padding(bottom = 32.dp),
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        ActionCard(
                            icon = Icons.Default.ChatBubble,
                            label = "명령어 채팅",
                            onClick = {
                                startActivity(Intent(this@MainActivity, ChatActivity1::class.java))
                            }
                        )
                        ActionCard(
                            icon = Icons.Default.Notifications,
                            label = "이벤트 알림",
                            onClick = {
                                startActivity(Intent(this@MainActivity, ChatActivity2::class.java))
                            }
                        )
                        ActionCard(
                            icon = Icons.Default.Place,
                            label = "실시간 지도",
                            onClick = {
                                startActivity(Intent(this@MainActivity, MapActivity::class.java))
                            }
                        )
                    }
                }
            }
        }
    }
}

// 카드 UI 컴포넌트
@Composable
fun ActionCard(icon: ImageVector, label: String, onClick: () -> Unit) {
    var scale by remember { mutableStateOf(1f) }
    Surface(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(vertical = 8.dp)
            .scale(scale)
            .clickable(
                onClick = {
                    scale = 0.95f
                    onClick()
                }
            )
            .animateContentSize(animationSpec = spring()),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(end = 16.dp)
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "이동",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

// 환영 메시지 컴포넌트
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Welcome to $name!",
        modifier = modifier
    )
}

// UI 미리보기
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CampusPatrolRobotAppTheme(darkTheme = true) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Campus Patrol Robot",
                modifier = Modifier.padding(bottom = 32.dp),
                style = MaterialTheme.typography.headlineMedium
            )
            ActionCard(Icons.Default.ChatBubble, "명령어 채팅") {}
            ActionCard(Icons.Default.Notifications, "이벤트 알림") {}
            ActionCard(Icons.Default.Place, "실시간 지도") {}
        }
    }
}