package com.example.smartcropsapp.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.ViewAgenda
import androidx.compose.material.icons.rounded.Air
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.InvertColors
import androidx.compose.material.icons.rounded.Spa
import androidx.compose.material.icons.rounded.Speed
import androidx.compose.material.icons.rounded.Thermostat
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.material.icons.rounded.Wifi
import androidx.compose.material.icons.rounded.WifiOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.smartcropsapp.components.VisibleScaffoldNavigation
import kotlinx.coroutines.delay


val smartCrops = listOf(
    TomatoCrop(
        1,
        "Little Green House",
        "https://th.bing.com/th/id/OIP.XU9cPvIaZPCFb2vw7hGXogHaE8?rs=1&pid=ImgDetMain",
        "Healthy crop, congrats!",
        true,
        true,
        45,
        "Germination",
        24.0f,
        65.0f,
        1013.0f,
        21.0f,
        800.0f
    ),
    TomatoCrop(
        2,
        "Tomatoes",
        "https://minnetonkaorchards.com/wp-content/uploads/2022/03/Tomato-Disease-Anthracnose-SS-1715603392.jpg",
        "Crop at risk!",
        true,
        false,
        127,
        "Vegetative Growth",
        22.0f,
        70.0f,
        1009.0f,
        20.5f,
        750.0f
    ),
    TomatoCrop(
        3,
        "My Cropsy",
        "https://www.thespruce.com/thmb/TMRYcJe21L7W5Rn2R8jDqIfVSCk=/5750x3829/filters:fill(auto,1)/top-tomato-growing-tips-1402587-11-c6d6161716fd448fbca41715bbffb1d9.jpg",
        "This crop is disconnected.",
        false,
        true,
        60,
        "Flowering",
        23.0f,
        68.0f,
        1011.0f,
        20.0f,
        780.0f
    )
)


@Composable
fun HomeScreen(navController: NavHostController) {
    var isHorizontalView by remember { mutableStateOf(false) }
    val opacity = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        opacity.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 3500)
        )
    }

    VisibleScaffoldNavigation(navController = navController) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Welcome!",
                    fontFamily = FontFamily.Cursive,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .alpha(opacity.value)
                        .padding(16.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(
                        onClick = { isHorizontalView = false },
                        enabled = isHorizontalView
                    ) {
                        Icon(
                            imageVector = Icons.Default.ViewAgenda,
                            contentDescription = null,
                        )
                    }

                    IconButton(
                        onClick = { isHorizontalView = true },
                        enabled = !isHorizontalView
                    ) {
                        Icon(
                            imageVector = Icons.Default.GridView,
                            contentDescription = null,
                        )
                    }
                }
            }
            Text(
                "Your Smart Crops: ",
                modifier = Modifier.padding(horizontal = 24.dp),
                textAlign = TextAlign.Justify,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            if (!isHorizontalView) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(smartCrops) { crop ->
                        SmartCropCardHorizontal(crop, navController)
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(smartCrops) { crop ->
                        SmartCropCardGrid(crop, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun SmartCropCardHorizontal(smartCrop: TomatoCrop, navController: NavHostController) {
    var showSummary by remember { mutableStateOf(false) }
    var displayedMessage by remember { mutableStateOf("") }
    var index by remember { mutableIntStateOf(0) }

    LaunchedEffect(smartCrop.statusMessage) {
        while (index < smartCrop.statusMessage.length) {
            displayedMessage += smartCrop.statusMessage[index]
            index++
            delay(50)
        }
    }

    if (showSummary) {
        AlertDialog(
            onDismissRequest = { showSummary = false },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Info,
                        contentDescription = "Crop Summary",
                        tint = if (!smartCrop.isConnected) Color.Gray else if (smartCrop.isHealthy) Color.Green else Color.Red,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = smartCrop.name, fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column(modifier = Modifier.padding(8.dp)) {
                    Image(
                        painter = rememberAsyncImagePainter(model = smartCrop.imageUrl),
                        contentDescription = "Crop Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (smartCrop.isConnected) Icons.Rounded.Wifi else Icons.Rounded.WifiOff,
                            contentDescription = if (smartCrop.isConnected) "Connected" else "Disconnected",
                            tint = if (smartCrop.isConnected) Color.Green else Color.Red,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = if (smartCrop.isConnected) "Connected" else "Disconnected")
                    }

                    Spacer(modifier = Modifier.height(8.dp))


                    Text(
                        text = smartCrop.statusMessage,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    SummaryItem(
                        icon = Icons.Rounded.Spa,
                        label = "Growth Phase",
                        value = smartCrop.growthPhase
                    )
                    SummaryItem(
                        icon = Icons.Rounded.Thermostat,
                        label = "Temperature",
                        value = "${smartCrop.temperature}°C",
                        color = Color.Red
                    )
                    SummaryItem(
                        icon = Icons.Rounded.InvertColors,
                        label = "Humidity",
                        value = "${smartCrop.humidity}%",
                        color = Color.Blue
                    )
                    SummaryItem(
                        icon = Icons.Rounded.Speed,
                        label = "Pressure",
                        value = "${smartCrop.pressure} hPa"
                    )
                    SummaryItem(
                        icon = Icons.Rounded.Air,
                        label = "Oxygen Level",
                        value = "${smartCrop.oxygenLevel}%",
                        color = Color.Gray
                    )
                    SummaryItem(
                        icon = Icons.Rounded.WbSunny,
                        label = "Light Intensity",
                        value = "${smartCrop.lightIntensity} lx",
                        color = Color.Yellow
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showSummary = false }) {
                    Text("Close", fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { showSummary = true },
                    onTap = {
                        navController.navigate("detailCropScreen/${smartCrop.id}")
                    }
                )
            }
            .alpha(if (!smartCrop.isConnected) 0.5f else 1f)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(smartCrop.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = smartCrop.name, fontWeight = FontWeight.Bold)
                Text(text = displayedMessage)
            }
            Spacer(modifier = Modifier.weight(1f))
            if (smartCrop.isConnected)
                Icon(
                    imageVector = Icons.Default.Circle,
                    contentDescription = null,
                    tint = if (smartCrop.isHealthy) Color.Green else Color.Red
                )
        }
    }
}

@Composable
fun SmartCropCardGrid(smartCrop: TomatoCrop, navController: NavHostController) {
    var showSummary by remember { mutableStateOf(false) }

    if (showSummary) {

        AlertDialog(
            onDismissRequest = { showSummary = false },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Info,
                        contentDescription = "Crop Summary",
                        tint = if (smartCrop.isHealthy) Color.Green else Color.Red,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = smartCrop.name, fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column(modifier = Modifier.padding(8.dp)) {
                    Image(
                        painter = rememberAsyncImagePainter(model = smartCrop.imageUrl),
                        contentDescription = "Crop Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (smartCrop.isConnected) Icons.Rounded.Wifi else Icons.Rounded.WifiOff,
                            contentDescription = if (smartCrop.isConnected) "Connected" else "Disconnected",
                            tint = if (smartCrop.isConnected) Color.Green else Color.Red,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = if (smartCrop.isConnected) "Connected" else "Disconnected")
                    }

                    Spacer(modifier = Modifier.height(8.dp))


                    Text(
                        text = smartCrop.statusMessage,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    SummaryItem(
                        icon = Icons.Rounded.Spa,
                        label = "Growth Phase",
                        value = smartCrop.growthPhase
                    )
                    SummaryItem(
                        icon = Icons.Rounded.Thermostat,
                        label = "Temperature",
                        value = "${smartCrop.temperature}°C",
                        color = Color.Red
                    )
                    SummaryItem(
                        icon = Icons.Rounded.InvertColors,
                        label = "Humidity",
                        value = "${smartCrop.humidity}%",
                        color = Color.Blue
                    )
                    SummaryItem(
                        icon = Icons.Rounded.Speed,
                        label = "Pressure",
                        value = "${smartCrop.pressure} hPa"
                    )
                    SummaryItem(
                        icon = Icons.Rounded.Air,
                        label = "Oxygen Level",
                        value = "${smartCrop.oxygenLevel}%",
                        color = Color.Gray
                    )
                    SummaryItem(
                        icon = Icons.Rounded.WbSunny,
                        label = "Light Intensity",
                        value = "${smartCrop.lightIntensity} lx",
                        color = Color.Yellow
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showSummary = false }) {
                    Text("Close", fontWeight = FontWeight.Bold)
                }
            }
        )

    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(8.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { showSummary = true },
                    onTap = {
                        navController.navigate("detailCropScreen/${smartCrop.id}")
                    }
                )
            }
            .alpha(
                if (!smartCrop.isConnected) 0.5f else
                    1f
            )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (smartCrop.isConnected)
                Icon(
                    imageVector = Icons.Default.Circle,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.TopEnd),
                    tint = if (smartCrop.isHealthy) Color.Green else Color.Red
                )


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = rememberAsyncImagePainter(smartCrop.imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = smartCrop.name,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}


@Composable
fun SummaryItem(icon: ImageVector, label: String, value: String, color: Color = Color.Black) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = label, fontWeight = FontWeight.Bold)
            Text(text = value)
        }
    }
}

open class SmartCrop(
    val id: Int,
    val name: String,
    val imageUrl: String,
    var statusMessage: String,
    val isConnected: Boolean,
    val isHealthy: Boolean
)


class TomatoCrop(
    id: Int,
    name: String,
    imageUrl: String,
    statusMessage: String,
    isConnected: Boolean,
    isHealthy: Boolean,
    val growthTime: Int,
    val growthPhase: String,
    val temperature: Float,
    var humidity: Float,
    val pressure: Float,
    val oxygenLevel: Float,
    val lightIntensity: Float
) : SmartCrop(id, name, imageUrl, statusMessage, isConnected, isHealthy)


