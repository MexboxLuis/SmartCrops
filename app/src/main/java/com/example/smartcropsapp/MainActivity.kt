package com.example.smartcropsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.example.compose.SmartCropsAppTheme
import com.example.smartcropsapp.navigation.SmartCropsNavigation
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {

    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        cameraExecutor = Executors.newSingleThreadExecutor()

        setContent {
            SmartCropsAppTheme {
                SmartCropsApp()
            }
        }


    }
    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}



@Composable
fun SmartCropsApp() {

    val navController = rememberNavController()

    SmartCropsNavigation(navController = navController)

}
