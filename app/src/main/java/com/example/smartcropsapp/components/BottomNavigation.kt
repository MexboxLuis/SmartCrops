package com.example.smartcropsapp.components


import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Nature
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.smartcropsapp.models.ItemsBottomNavigation
import com.example.smartcropsapp.navigation.currentRoute
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun BottomNavigation(
    navController: NavHostController
) {

    val menuItems = listOf(
        ItemsBottomNavigation.FirstBottomItem,
        ItemsBottomNavigation.MiddleBottomItem,
        ItemsBottomNavigation.SecondBottomItem

    )
    var showBottomSheet by remember { mutableStateOf(false) }

    BottomAppBar {
        NavigationBar {
            menuItems.forEach { item ->
                val selected = currentRoute(navController = navController)
                NavigationBarItem(
                    selected = selected == item.route,
                    onClick = {
                        if (item.route == "null")
                            showBottomSheet = true
                        else if (selected != item.route)
                            navController.navigate(item.route)

                    },
                    icon = {
                        val isSelected = showBottomSheet
                        val scale = animateFloatAsState(
                            targetValue = if (isSelected) 2.2f else 1.5f,
                            label = ""
                        )
                        if (item.route != "null")
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                        else
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                modifier = Modifier.scale(scale.value)
                            )
                    },
                    label = {
                        Text(item.title)
                    }

                )
            }
        }
    }

    if (showBottomSheet) {
        BottomSheet(onDismiss = { showBottomSheet = false })
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(onDismiss: () -> Unit) {

    var isAlertDialog by remember { mutableStateOf(false) }
    var isSearching by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    val currentDate =
        remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()) }

    ModalBottomSheet(
        onDismissRequest = {
            scope.launch { sheetState.hide() }
            onDismiss()
        },
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Where tomatoes thrive.",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Light,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(48.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CreatingActionsIcon(
                    Icons.Default.Nature,
                    text = "Add your SmartCrop",
                    actionButton = { isSearching = true })
                CreatingActionsIcon(
                    Icons.Default.PostAdd,
                    text = "Post your own tomato",
                    actionButton = { isAlertDialog = true })
            }
        }
        Spacer(modifier = Modifier.height(48.dp))

        if (isAlertDialog)
            AlertDialogContent(currentDate, onDismiss = { isAlertDialog = false })

        Spacer(modifier = Modifier.height(64.dp))

        if (isSearching)
            SearchingDevicesDialog(onDismiss = { isSearching = false })
    }
}

@Composable
fun AlertDialogContent(currentDate: String, onDismiss: () -> Unit) {

    var description by remember { mutableStateOf("") }
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "@guest99",
                    fontWeight = FontWeight.Light,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                )

                Text(
                    text = currentDate,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                IconButton(
                    onClick = {
                        onDismiss()
                    },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close"
                    )
                }
            }
        }

        item {
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
        }


        item {
            ExtendedFloatingActionButton(
                onClick = { },
                icon = {
                    Icon(
                        Icons.Default.CameraAlt,
                        contentDescription = null
                    )
                },
                text = { Text("Add picture") },
                modifier = Modifier.fillMaxWidth()
            )


            Spacer(modifier = Modifier.height(32.dp))

            if (description.isNotBlank())


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = {
                        Toast.makeText(context, "Uploading...", Toast.LENGTH_SHORT).show()
                        onDismiss()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Upload,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp)
                        )
                    }
                }
        }
    }
}


@Composable
fun CreatingActionsIcon(
    imageVector: ImageVector,
    text: String = "",
    actionButton: () -> Unit = {}
) {


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Box(
            modifier = Modifier
                .size(76.dp)
                .border(
                    BorderStroke(1.dp, MaterialTheme.colorScheme.scrim),
                    RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))
                .clickable { actionButton() }
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )

    }

}

@Composable
fun SearchingDevicesDialog(onDismiss: () -> Unit) {


    var isSearching by remember { mutableStateOf(true) }


    AlertDialog(
        onDismissRequest = {
            isSearching = false
            onDismiss()
        },
        confirmButton = {
            Button(
                onClick = {
                    isSearching = false
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancel")
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AnimatedVisibility(visible = isSearching) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Searching for devices",
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(50.dp)
                                .padding(bottom = 32.dp)
                        )
                    }
                }

            }
        }
    )
}



