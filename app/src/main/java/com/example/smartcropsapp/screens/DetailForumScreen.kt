package com.example.smartcropsapp.screens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.smartcropsapp.components.TopPopUpNavigation


@Composable
fun DetailForumScreen(
    username: String,
    date: String,
    content: String,
    photos: List<String>,
    initialLikes: Int,
    initialComments: List<String>,
    navController: NavHostController
) {
    var likes by remember { mutableIntStateOf(initialLikes) }
    var isLiked by remember { mutableStateOf(false) }
    var comments by remember { mutableStateOf(initialComments) }
    var newComment by remember { mutableStateOf("") }
    var selectedImage by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = { TopPopUpNavigation(navController = navController, title = username) },
        bottomBar = {
            BottomAppBar {
                Row(modifier = Modifier.fillMaxWidth()) {
                    IconButton(onClick = {
                        isLiked = !isLiked
                        likes = if (isLiked) likes + 1 else likes - 1
                    }) {
                        Icon(
                            imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite"
                        )
                    }

                    TextField(
                        value = newComment,
                        onValueChange = { newComment = it },
                        label = { Text("Your comment") },
                        modifier = Modifier
                    )

                    IconButton(
                        onClick = {
                            if (newComment.isNotBlank()) {
                                comments = comments + "$newComment - guest99"
                                newComment = ""
                            }
                        },
                        enabled = newComment.isNotBlank()
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Comment"
                        )
                    }
                }
            }
        }

    ) { padding ->


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                item {
                    Text(text = "Date: $date", fontSize = 16.sp, color = Color.Gray)

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 16.dp),
                        thickness = 1.dp,
                        color = Color.LightGray
                    )
                }

                item { Text(text = content, fontSize = 16.sp) }

                item {
                    photos.forEach { photo ->
                        AsyncImage(
                            model = photo,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable { selectedImage = photo }
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = null,
                                tint = Color.Red
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "$likes Likes",
                                fontSize = 16.sp
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Comment,
                                contentDescription = null,
                                tint = Color.Cyan
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${comments.size} Comments",
                                fontSize = 16.sp
                            )
                        }
                    }

                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Comments:",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    comments.reversed().forEach { comment ->
                        val parts = comment.split(" - ")
                        val commentText = parts.getOrElse(0) { "" }
                        val commentUser = parts.getOrElse(1) { "guest99" }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(bottom = 4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .border(
                                            BorderStroke(
                                                1.dp,
                                                MaterialTheme.colorScheme.inverseSurface
                                            ),
                                            CircleShape
                                        )
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = commentUser,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Gray
                                )
                            }

                            Text(
                                text = commentText,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(start = 32.dp)
                            )

                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 8.dp),
                                thickness = 1.dp,
                                color = Color.LightGray
                            )
                        }
                    }
                }
            }
        }
    }

    selectedImage?.let { imageUri ->
        ImageViewerDialog(
            imageUri = imageUri,
            onDismiss = { selectedImage = null }
        )
    }

}


@Composable
fun ImageViewerDialog(
    imageUri: String,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(326.dp),
            shape = RoundedCornerShape(8.dp),
            color = Color.Black.copy(alpha = 0f)

        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),

                    )
                IconButton(
                    onClick = { onDismiss() },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close"
                    )
                }
            }
        }
    }
}



