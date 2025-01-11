package com.example.smartcropsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.smartcropsapp.screens.CameraPreviewScreen
import com.example.smartcropsapp.screens.DetailCropScreen
import com.example.smartcropsapp.screens.DetailForumScreen
import com.example.smartcropsapp.screens.ForumScreen
import com.example.smartcropsapp.screens.HomeScreen
import com.example.smartcropsapp.screens.SliderScreen
import com.example.smartcropsapp.screens.TomaBotScreen
import com.example.smartcropsapp.screens.forumPosts
import com.example.smartcropsapp.screens.getTomatoCropById

@Composable
fun SmartCropsNavigation(
    navController: NavHostController
) {

    NavHost(navController = navController, startDestination = NavScreen.HomeScreen.name) {
        composable(NavScreen.HomeScreen.name) {
            HomeScreen(navController = navController)
        }
        composable(NavScreen.ForumScreen.name) {
            ForumScreen(navController = navController)

        }

        composable(
            "detailCropScreen/{cropId}",
            arguments = listOf(navArgument("cropId") { type = NavType.IntType })
        ) { backStackEntry ->
            val cropId = backStackEntry.arguments?.getInt("cropId") ?: -1
            val crop = getTomatoCropById(cropId)

            crop?.let {
                DetailCropScreen(crop = it, navController = navController)
            }
        }

        composable(
            "sliderScreen/{cropId}",
            arguments = listOf(navArgument("cropId") { type = NavType.IntType })
        ) { backStackEntry ->
            val cropId = backStackEntry.arguments?.getInt("cropId") ?: -1
            val crop = getTomatoCropById(cropId)

            crop?.let {
                SliderScreen(crop = it, navController = navController)
            }
        }


        composable(
            "detailScreen/{postId}",
            arguments = listOf(
                navArgument("postId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId") ?: ""
            val post = forumPosts.find { it.id.toString() == postId }

            post?.let {
                DetailForumScreen(
                    username = it.username,
                    date = it.date,
                    content = it.content,
                    photos = it.photos.orEmpty(),
                    initialLikes = it.likes,
                    initialComments = it.comments,
                    navController
                )
            }
        }

        composable("TomaBotScreen"){
            TomaBotScreen(navController = navController)
        }

        composable("CameraXPreview"){
            CameraPreviewScreen(onClose = { navController.navigate("HomeScreen") })
        }
    }
}