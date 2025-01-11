package com.example.smartcropsapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.smartcropsapp.components.VisibleScaffoldNavigation


val forumPosts = listOf(
    ForumPostData(
        id = 1,
        username = "Matsumoto123",
        date = "20/08/2024",
        content = "This is the content of the first post. welcome to the forum!",
        photos = null,
        likes = 10,
        comments = listOf(
            "Excited to be part of this community! - Sarah",
            "Hello everyone, looking forward to learning new things! - John",
            "Just joined, can't wait to share my gardening experiences! - Alice",
            "This forum seems like a great resource! - David",
            "Thanks for the warm welcome, everyone! - Emily"
        )
    ),
    ForumPostData(
        id = 2,
        username = "TomatoLover",
        date = "22/08/2024",
        content = "Here is my experience growing tomatoes in a greenhouse.",
        photos = listOf("https://www.thermofisher.com/blog/wp-content/uploads/sites/5/2014/11/tomato.jpg", "https://agrilifetoday.tamu.edu/wp-content/uploads/2023/03/20210604_The_Gardens_LM_0082.jpg"),
        likes = 5,
        comments = listOf(
            "Those tomatoes look fantastic! How did you achieve that size? - Peter",
            "I'm definitely going to try growing tomatoes in my greenhouse now! - Mary"
        )
    ),

    ForumPostData(
        id = 3,
        username = "GreenThumbGuru",
        date = "22/08/2024",
        content = "August's Plant of the Month: Basil. Learn how to grow this aromatic herb in your greenhouse.",
        photos = null,
        likes = 15,
        comments = listOf(
            "Basil is my favorite herb! I love using it in fresh pesto.  -  Jane",
            "Thanks for the tips! I'm definitely going to try growing basil this year. - Charles",
            "This sounds like a fun challenge! Basil is next on my list. - Brenda",
            "I love the smell of fresh basil! - William",
            "Do you have any recipes for basil pesto to share? - Michael",
            "I might try planting basil in my outdoor garden as well. - Diana",
            "This is a very informative post, thank you! - Robert",
            "Great post, GreenThumbGuru! - Steven"
        )
    ),
    ForumPostData(
        id = 4,
        username = "GardeningGal",
        date = "22/08/2024",
        content = "Tips for Controlling Aphids in Your Greenhouse: Natural Remedies and Prevention Tips.",
        photos = listOf("url_to_aphids_photo_1", "url_to_aphids_photo_2"),
        comments = listOf(
            "Thank you so much for these tips! Aphids have been a real problem for me lately. -  Elizabeth",
            "I've been struggling with aphids too! I'll definitely try these natural remedies. - Daniel",
            "These tips are very helpful, thanks for sharing! - Christopher"
        )
    ),
    ForumPostData(
        id = 5,
        username = "GreenhousePro",
        date = "23/08/2024",
        content = "How to Maintain Optimal Humidity Levels in Your Greenhouse: A Guide.",
        photos = listOf("url_to_humidity_photo_1", "url_to_humidity_photo_2"),
        likes = 20,
        comments = listOf(
            "Humidity control is crucial for healthy plant growth! Thanks for sharing this helpful guide. -  Katherine",
            "This is exactly what I needed! I've been struggling to maintain consistent humidity levels, especially during the winter. -  Alex",
            "Thanks for sharing your knowledge! I'll definitely be implementing these tips in my greenhouse. -  Gregory",
            "I'll be referencing this guide whenever I need to adjust the humidity in my greenhouse. -  Daniel",
            "Great tips! I've noticed a big improvement in my plants' health since I started following these guidelines. -  Emily",
            "I've been experimenting with different humidity control methods, but this guide has given me a solid foundation. -  Michael",
            "Do you have any recommendations for humidifiers that are specifically designed for greenhouses? -  Sarah",
            "Thanks for the detailed explanations! I now have a much better understanding of why humidity is so important. -  Benjamin",
            "I'm going to share this with my gardening club. -  Christopher"
        )
    ),
    ForumPostData(
        id = 6,
        username = "DIYMaster",
        date = "26/08/2024",
        content = "DIY Greenhouse Shelves: A Step-by-Step Guide.",
        photos = listOf("url_to_shelves_photo_1"),
        comments = listOf(
            "I've been wanting to build my own greenhouse shelves for a while now. This guide is perfect! -  Elizabeth",  // Expresses excitement
            "Great DIY project! I love finding ways to customize my greenhouse. -  Daniel",
            "Can't wait to try this! I'm going to use reclaimed wood for a rustic look. -  Christopher",
            "Thank you for the detailed instructions! I'm feeling confident that I can build these shelves myself. -  Andrew",
            "These shelves look so sturdy and well-made. I'm impressed! -  Margaret",
            "Do you have any recommendations for the best type of wood to use for greenhouse shelves? -  Sophia",  // Asks for specific advice
            "I'm going to add this project to my list. Thanks for sharing! -  Thomas"
        )
    ),
    ForumPostData(
        id = 7,
        username = "ContestKing",
        date = "27/08/2024",
        content = "Share photos of your largest tomato harvest, here you can make your life green :), although is boring, hahahahahahahah",
        photos = null,
        likes = 50,
        comments = listOf(
            "I've got a giant tomato growing in my garden! I'll definitely be entering this contest. -  Abigail",  // Expresses intent to participate
            "This is such a fun contest! I can't wait to see everyone's entries. -  Benjamin",
            "I'm going to grow the biggest tomato ever, just for this contest! -  Charlotte",
            "I love the humor in your post! Makes gardening even more fun. -  Daniel",
            "I'm going to share a photo of my tomato plant, even though it's not the biggest. -  Emily",  // Shares personal experience
            "I've never grown tomatoes before, but I'm inspired to try now! -  Francis",
            "Can't wait to see the winning entry! -  George",
            "This contest is a great way to connect with other gardening enthusiasts. -  Hannah",
            "I'm so excited to see all the different varieties of tomatoes! -  Isaac"
        )
    )
)

@Composable
fun ForumScreen(navController: NavHostController) {

    VisibleScaffoldNavigation(navController = navController) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Forum-ato",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),

                ) {

                forumPosts.reversed().forEach { post ->
                    item {
                        ForumPost(
                            username = post.username,
                            date = post.date,
                            content = post.content,
                            photos = post.photos,
                            likes = post.likes,
                            comments = post.comments.size,
                            onClick = {
                                navController.navigate("detailScreen/${post.id}")
                            }

                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

            }
        }
    }


}


@Composable
fun ForumPost(
    username: String,
    date: String,
    content: String,
    photos: List<String>?,
    likes: Int,
    comments: Int,
    onClick: () -> Unit
) {


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(
                            BorderStroke(2.dp, MaterialTheme.colorScheme.inverseSurface),
                            CircleShape
                        )
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = username,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Text(
                text = date,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp,
        )

        Text(
            text = content,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(top = 8.dp)
                .heightIn(min = 40.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        HorizontalDivider(
            modifier = Modifier.padding(top = 8.dp),
            thickness = 1.dp,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "$likes Likes",
                    fontSize = 14.sp
                )
            }

            if (photos != null) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AddPhotoAlternate,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${photos.size} photos",
                        fontSize = 14.sp
                    )
                }

            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Comment,
                    contentDescription = null,
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "$comments Comments",
                    fontSize = 14.sp
                )
            }
        }
    }
}

data class ForumPostData(
    val id: Int,
    val username: String,
    val date: String,
    val content: String,
    val photos: List<String>? = null,
    val likes: Int = 0,
    val comments: List<String> = emptyList()
)






