package com.example.smartcropsapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.smartcropsapp.R
import com.example.smartcropsapp.navigation.NavScreen
import com.example.smartcropsapp.navigation.currentRoute



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisibleScaffoldNavigation(navController: NavHostController, content: @Composable () -> Unit) {

    val appName = stringResource(id = R.string.app_name)
    val qrIcon = Icons.Filled.QrCodeScanner

    Scaffold(

        bottomBar = {
            if (currentRoute(navController) == NavScreen.HomeScreen.name || currentRoute(
                    navController
                ) == NavScreen.ForumScreen.name
            )
                BottomNavigation(navController)
        },
        topBar = {

            if (currentRoute(navController) == NavScreen.HomeScreen.name)
                TopAppBar(
                    title = { Text(appName) },
                    actions = {
                        IconButton(onClick = { navController.navigate("CameraXPreview") }) {
                            Icon(imageVector = qrIcon, contentDescription = null)
                        }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
        },
        floatingActionButton = {
            if (currentRoute(navController) == NavScreen.HomeScreen.name)
                FloatingActionButton(onClick = { navController.navigate("TomaBotScreen") }) {
                    Image(painter = painterResource(id = R.drawable.toma_bot), contentDescription = null, modifier = Modifier.size(48.dp))
                }
        }

    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            content()
        }
    }
}