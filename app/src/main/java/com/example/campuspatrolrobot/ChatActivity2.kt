package com.example.campuspatrolrobot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.example.campuspatrolrobot.ui.theme.CampusPatrolRobotAppTheme

class ChatActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 화면 설정
        setContent {
            CampusPatrolRobotAppTheme(darkTheme = true) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EventScreen()
                }
            }
        }
    }
}

// 이벤트 알림 화면 구성
@Composable
fun EventScreen(modifier: Modifier = Modifier) {
    val events = remember { mutableStateListOf<String>() }
    LaunchedEffect(Unit) {
        events.add("2025-06-05 06:08: 킥보드 감지 - 지도: https://maps.example.com/123")
        events.add("2025-06-05 06:15: 킥보드 감지 - 지도: https://maps.example.com/456")
    }

    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "이벤트 알림",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            reverseLayout = true
        ) {
            items(events.size) { index ->
                val reversedIndex = events.size - 1 - index
                val event = events[reversedIndex]
                EventItem(event = event)
            }
        }
    }
}

// 이벤트 항목 UI 컴포넌트
@Composable
fun EventItem(event: String) {
    var expanded by remember { mutableStateOf(false) }
    var scale by remember { mutableStateOf(1f) }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .scale(scale)
            .clickable(
                onClick = {
                    scale = 0.95f
                    expanded = expanded.not()
                }
            )
            .animateContentSize(animationSpec = spring()),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = event,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium
            )
            if (expanded) {
                Text(
                    text = "상세 정보: 이벤트 세부 사항 표시 (스테이지 9에서 구현)",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}