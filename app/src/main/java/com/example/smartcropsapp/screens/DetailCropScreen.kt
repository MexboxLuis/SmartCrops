package com.example.smartcropsapp.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.smartcropsapp.R
import com.example.smartcropsapp.components.TopPopUpNavigation


fun getTomatoCropById(cropId: Int): TomatoCrop? {
    return smartCrops.find { it.id == cropId }
}


@Composable
fun DetailCropScreen(crop: TomatoCrop, navController: NavHostController) {
    val growthPhaseDetails = calculateGrowthPhaseDetails(crop.growthTime)
    var showGifDialog by remember { mutableStateOf(false) }
    var showSuggestionDialog by remember { mutableStateOf(false) }
    var isCorrecting by remember { mutableStateOf(false) }
    var hasNavigated by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                if (!hasNavigated)
                    detectDragGestures { change, _ ->
                        if (change.position.y < change.previousPosition.y) {
                            hasNavigated = true
                            navController.navigate("sliderScreen/${crop.id}")
                        }
                    }
            }
    ) {
        Scaffold(
            topBar = {
                TopPopUpNavigation(
                    navController = navController,
                    title = crop.name,
                    isForumScreen = false
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(modifier = Modifier.padding(32.dp)) {
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                            .border(
                                width = 4.dp,
                                color = if (!crop.isConnected) Color.Gray.copy(alpha = 0.75f) else if (crop.isHealthy) Color.Green else Color.Red,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = growthPhaseDetails.imageResId),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                        )
                    }

                    IconButton(
                        onClick = { showGifDialog = true },
                        modifier = Modifier
                            .size(48.dp)
                            .background(MaterialTheme.colorScheme.surface, CircleShape)
                            .padding(4.dp)
                            .align(Alignment.TopEnd),
                        enabled = crop.isConnected
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Camera Icon",
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (crop.isConnected) {

                    if (!crop.isHealthy) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Unhealthy",
                                color = Color.Red,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            IconButton(
                                onClick = { showSuggestionDialog = true }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = "Warning Icon",
                                    modifier = Modifier.border(4.dp, Color.Yellow)


                                )
                            }
                        }
                    } else {
                        Text(
                            text = "Healthy",
                            color = Color.Green,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Card(
                        modifier = Modifier
                            .padding(16.dp)
                            .border(2.dp, Color.LightGray, RoundedCornerShape(8.dp)),
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = growthPhaseDetails.phase,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = growthPhaseDetails.daysRemainingMessage)
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    if (showSuggestionDialog) {
                        SuggestionDialog(
                            onDismiss = { showSuggestionDialog = false },
                            onCorrect = {
                                showSuggestionDialog = false
                                isCorrecting = true
                            }
                        )
                    }


                    if (isCorrecting) {
                        CircularProgressIndicator()
                        LaunchedEffect(Unit) {
                            kotlinx.coroutines.delay(2000)
                            isCorrecting = false
                            crop.humidity = adjustHumidityForPhase(crop.growthPhase)
                            crop.statusMessage = "Healthy"
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                    SwipeUpHint()

                    if (showGifDialog) {

                        Dialog(onDismissRequest = { showGifDialog = false }) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(328.dp)
                                    .background(Color.Black.copy(alpha = 0.5f)),
                                contentAlignment = Alignment.Center
                            ) {
                                GifImage(url = "https://i.pinimg.com/originals/6f/a8/12/6fa812d488735737aec39718663e6486.gif")
                            }
                        }
                    }
                } else {
                    Text(text = "This crop is not connected :(")

                    Spacer(modifier = Modifier.height(32.dp))

                    TextButton(onClick = { /*TODO*/ }) {
                        Text("If you can, check it out!")
                    }

                }
            }
        }
    }
}


@Preview
@Composable
fun DetailCropScreenPreview() {
    DetailCropScreen(crop = smartCrops[0], navController = NavHostController(LocalContext.current))
}


@Composable
fun SuggestionDialog(onDismiss: () -> Unit, onCorrect: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Suggestions") },
        text = { Text("It seems like the humidity level is too low for this growth phase. Would you like to correct it?") },
        confirmButton = {
            Button(onClick = onCorrect) {
                Text("Correct")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Dismiss")
            }
        }
    )
}

fun adjustHumidityForPhase(phase: String): Float {
    return when (phase) {
        "Initial Phase" -> 80f
        "Vegetative Phase" -> 70f
        "Flowering Phase" -> 65f
        "Reproductive Phase" -> 60f
        "Final Maturation" -> 55f
        else -> 60f
    }
}


@Composable
fun GifImage(url: String) {

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = null,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun SwipeUpHint() {

    val animatedOffset = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            animatedOffset.animateTo(
                targetValue = -20f,
                animationSpec = tween(durationMillis = 500, easing = LinearEasing)
            )
            animatedOffset.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 500, easing = LinearEasing)
            )
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Swipe up for more details", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        Icon(
            imageVector = Icons.Default.KeyboardArrowUp,
            contentDescription = "Swipe Up",
            modifier = Modifier
                .size(48.dp)
                .offset(y = animatedOffset.value.dp),
            tint = Color.Gray
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SliderScreen(crop: TomatoCrop, navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { crop.name },
                navigationIcon = {

                    IconButton(
                        onClick = { navController.navigate("detailCropScreen/${crop.id}") }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = null)

                    }
                })

        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(padding)
        ) {
            CropSlider("Temperature", crop.temperature, -10f, 100f, "°C")
            CropSlider("Humidity", crop.humidity, 0f, 100f, "%")
            CropSlider("Pressure", crop.pressure, 900f, 1100f, "hPa")
            CropSlider("Oxygen Level", crop.oxygenLevel, 0f, 100f, "%")
            CropSlider("Light Intensity", crop.lightIntensity, 0f, 2000f, "lx")
        }
    }
}

@Composable
fun CropSlider(label: String, value: Float, min: Float, max: Float, unit: String) {
    Text(text = "$label ($value$unit)", fontWeight = FontWeight.Bold)
    Slider(
        value = value,
        onValueChange = {},
        valueRange = min..max,
        steps = 9,
        enabled = false,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))
}


data class GrowthPhaseDetails(
    val phase: String,
    val daysRemainingMessage: String,
    val imageResId: Int
)

fun calculateGrowthPhaseDetails(growthTime: Int): GrowthPhaseDetails {
    return when (growthTime) {
        in 1..21 -> {
            GrowthPhaseDetails(
                phase = "Initial Phase",
                daysRemainingMessage = "You have less than ${22 - growthTime} days until the Vegetative Phase.",
                imageResId = R.drawable.phase_initial
            )
        }

        in 22..49 -> {
            GrowthPhaseDetails(
                phase = "Vegetative Phase",
                daysRemainingMessage = "You have less than ${50 - growthTime} days until the Flowering Phase.",
                imageResId = R.drawable.phase_vegetative
            )
        }

        in 50..80 -> {
            GrowthPhaseDetails(
                phase = "Flowering Phase",
                daysRemainingMessage = "You have less than ${81 - growthTime} days until the Reproductive Phase.",
                imageResId = R.drawable.phase_flowering
            )
        }

        in 81..100 -> {
            GrowthPhaseDetails(
                phase = "Reproductive Phase",
                daysRemainingMessage = "You have less than ${101 - growthTime} days until the Final Maturation and Harvest Phase.",
                imageResId = R.drawable.phase_reproductive
            )
        }

        in 101..180 -> {
            GrowthPhaseDetails(
                phase = "Final Maturation",
                daysRemainingMessage = "The crop is nearing harvest time!",
                imageResId = R.drawable.phase_final
            )
        }

        else -> {
            GrowthPhaseDetails(
                phase = "Unknown Phase",
                daysRemainingMessage = "The growth time is out of expected bounds.",
                imageResId = R.drawable.unknow
            )
        }
    }
}

