package com.example.campuspatrolrobot

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.campuspatrolrobot.ui.theme.CampusPatrolRobotAppTheme

class ChatActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

// 이벤트 저장 함수 (EventScreen 밖으로 이동)
fun saveEvents(context: Context, events: List<String>) {
    val prefs = context.getSharedPreferences("EventLogs", Context.MODE_PRIVATE)
    prefs.edit().putStringSet("logs", events.toSet()).apply()
}

@Composable
fun EventScreen(modifier: Modifier = Modifier) {
    val events = remember { mutableStateListOf<String>() }
    val context = LocalContext.current
    var showClearDialog by remember { mutableStateOf(false) }

    // SharedPreferences에서 이벤트 로그 불러오기
    LaunchedEffect(Unit) {
        val prefs = context.getSharedPreferences("EventLogs", Context.MODE_PRIVATE)
        val savedLogs = prefs.getStringSet("logs", emptySet())?.toList() ?: emptyList()
        events.addAll(savedLogs)
        // 더미 데이터 20개 추가 (기존 로그 뒤에)
        val baseTime = "2025-06-07 11:"
        val baseLat = 37.7749
        val baseLng = -122.4194
        for (i in 0..19) {
            val minute = 50 + i
            val time = if (minute < 60) "$baseTime$minute" else "$baseTime${minute % 60}"
            val lat = baseLat + i * 0.0001
            val lng = baseLng + i * 0.0001
            val event = "$time: 킥보드 감지 - 지도: https://www.google.com/maps?q=$lat,$lng&z=18"
            if (event !in events) {
                events.add(event)
                saveEvents(context, events)
            }
        }
    }

    // 이벤트 초기화 함수
    fun clearEvents() {
        events.clear()
        val prefs = context.getSharedPreferences("EventLogs", Context.MODE_PRIVATE)
        prefs.edit().remove("logs").apply()
    }

    // 로그 지우기 다이얼로그
    if (showClearDialog) {
        AlertDialog(
            onDismissRequest = { showClearDialog = false },
            title = { Text("이벤트 로그 지우기") },
            text = { Text("모든 이벤트 로그를 삭제하시겠습니까?") },
            confirmButton = {
                Button(onClick = {
                    clearEvents()
                    showClearDialog = false
                }) {
                    Text("삭제")
                }
            },
            dismissButton = {
                Button(onClick = { showClearDialog = false }) {
                    Text("취소")
                }
            }
        )
    }

    Column(modifier = modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "이벤트 알림",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Button(
                onClick = { showClearDialog = true },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("로그 지우기")
            }
        }
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

@Composable
fun EventItem(event: String) {
    var expanded by remember { mutableStateOf(false) }
    var scale by remember { mutableStateOf(1f) }
    val context = LocalContext.current
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .scale(scale)
            .clickable {
                scale = 0.95f
                val url = event.substringAfter("지도: ").trim()
                if (url.isNotEmpty()) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                }
            }
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