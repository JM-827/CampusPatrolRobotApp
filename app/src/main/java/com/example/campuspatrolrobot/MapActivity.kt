package com.example.campuspatrolrobot

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.campuspatrolrobot.ui.theme.CampusPatrolRobotAppTheme
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : ComponentActivity() {
    // 기능: GoogleMap 객체를 나중에 초기화 (MapScreen에서 사용)
    private lateinit var googleMap: GoogleMap

    // 초기 환경 설정: 위치 권한 요청 및 디버깅용 로깅
    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val coarseLocationGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false
        if (fineLocationGranted && coarseLocationGranted) {
            Log.d("MapActivity", "Location permissions granted") // 디버깅: 권한 승인 확인
        } else {
            Log.e("MapActivity", "Location permissions denied") // 디버깅: 권한 거부 오류
            finish() // UX: 권한 없으면 앱 종료
        }
    }

    // 초기 설정: 액티비티 시작 시 실행, 기본 설계도 커스터마이징
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 초기 설정: Google Play Services 가용성 확인
        val resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
        if (resultCode != ConnectionResult.SUCCESS) {
            Log.e("MapActivity", "Google Play Services unavailable: $resultCode") // 디버깅
            if (GoogleApiAvailability.getInstance().isUserResolvableError(resultCode)) {
                GoogleApiAvailability.getInstance().getErrorDialog(this, resultCode, 1)?.show()
            }
            finish() // 서비스 없으면 앱 종료
            return
        } else {
            Log.d("MapActivity", "Google Play Services available") // 디버깅
        }

        // 초기 설정: 위치 권한 요청 (지도 기능 필수)
        locationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        // 핵심: Compose UI 설정, MapScreen 컴포저블 호출
        try {
            setContent {
                CampusPatrolRobotAppTheme(darkTheme = true) {
                    MapScreen()
                }
            }
        } catch (e: Exception) {
            Log.e("MapActivity", "Error in onCreate: ${e.message}", e) // 디버깅
            finish()
        }
    }

    // 컴포저블: 지도 UI+기능 덩어리, 프로젝트 내 재사용 가능 (내가 jetpack 에서 제공하는 기능 블럭들로 새로운 기능 만듬)
    @Composable
    fun MapScreen(modifier: Modifier = Modifier) {
        val context = LocalContext.current
        // 초기 설정: MapView 생성
        val mapView = remember { MapView(context).apply { onCreate(null) } }

        // 기능: Google Maps 표시 및 마커/카메라 설정
        AndroidView(
            factory = { mapView },
            modifier = modifier.fillMaxSize(),
            update = {
                mapView.getMapAsync { map ->
                    googleMap = map
                    try {
                        googleMap.uiSettings.isZoomControlsEnabled = true
                        googleMap.moveCamera(CameraUpdateFactory.zoomTo(18f))
                        val testLatLng = LatLng(37.7749, -122.4194)
                        googleMap.addMarker(MarkerOptions().position(testLatLng).title("테스트 위치"))
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(testLatLng))
                    } catch (e: Exception) {
                        Log.e("MapActivity", "Error in map setup: ${e.message}", e) // 디버깅
                    }
                }
            }
        )

        // 기능: MapView 생명주기 관리
        LaunchedEffect(Unit) {
            mapView.onStart()
            mapView.onResume()
        }
        DisposableEffect(Unit) {
            onDispose {
                mapView.onPause()
                mapView.onStop()
                mapView.onDestroy()
            }
        }
    }
}
