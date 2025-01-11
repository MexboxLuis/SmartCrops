package com.example.smartcropsapp.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.smartcropsapp.navigation.NavScreen

sealed class ItemsBottomNavigation(
    val icon: ImageVector,
    val title: String,
    val route: String
) {
    object FirstBottomItem :
        ItemsBottomNavigation(Icons.Filled.Home, "Home", NavScreen.HomeScreen.name)

    object SecondBottomItem :
        ItemsBottomNavigation(Icons.Filled.Forum, "Forum", NavScreen.ForumScreen.name)

    object MiddleBottomItem :
            ItemsBottomNavigation(Icons.Filled.AddCircle, "" , "null")

}