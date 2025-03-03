package com.example.tourismchat1.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tourismchat1.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val infiniteTransition = rememberInfiniteTransition()
    val animateProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bhojpur District Tourism", color = Color.White) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFF00796B)
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFFF1F8E9), Color(0xFFE1F5FE)),
                        start = Offset(animateProgress * 1000, animateProgress * 1000),
                        end = Offset(1000f + animateProgress * 1000, 1000f + animateProgress * 1000)
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Animated Logo
                var logoVisible by remember { mutableStateOf(false) }
                val logoScale by animateFloatAsState(
                    targetValue = if (logoVisible) 1f else 0.7f,
                    animationSpec = spring(dampingRatio = 0.6f, stiffness = 100f)
                )
                val logoAlpha by animateFloatAsState(
                    targetValue = if (logoVisible) 1f else 0f,
                    animationSpec = tween(800)
                )

                LaunchedEffect(Unit) { logoVisible = true }

                Image(
                    painter = painterResource(id = R.drawable.bihar_tourism_logo),
                    contentDescription = "Tourism Logo",
                    modifier = Modifier
                        .size(180.dp)
                        .graphicsLayer {
                            scaleX = logoScale
                            scaleY = logoScale
                            alpha = logoAlpha
                        }
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Staggered Buttons
                val buttons = listOf(
                    Triple("View Attractions", R.drawable.baseline_travel_explore_24) { navController.navigate("attractions") },
                    Triple("View Map", R.drawable.baseline_map_24) { navController.navigate("map") },
                    Triple("Itinerary Planner", R.drawable.itinerary_icon) { navController.navigate("itinerary") },
                    Triple("About Bhojpur", R.drawable.profile_icon) { navController.navigate("about") }
                )

                buttons.forEachIndexed { index, (text, icon, action) ->
                    var visible by remember { mutableStateOf(false) }

                    LaunchedEffect(Unit) {
                        delay(500 + index * 150L)
                        visible = true
                    }

                    AnimatedVisibility(
                        visible = visible,
                        enter = slideInHorizontally(
                            initialOffsetX = { if (index % 2 == 0) it / 2 else -it / 2 }
                        ) + fadeIn() + scaleIn(),
                        modifier = Modifier.fillMaxWidth(0.85f)
                    ) {
                        AnimatedButton(
                            text = text,
                            iconResId = icon,
                            onClick = action
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun AnimatedButton(text: String, iconResId: Int, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val elevation by animateDpAsState(if (isPressed) 8.dp else 4.dp)
    val scale by animateFloatAsState(if (isPressed) 0.97f else 1f)

    Surface(
        modifier = Modifier
            .shadow(elevation, RoundedCornerShape(12.dp))
            .graphicsLayer(scaleX = scale, scaleY = scale),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
        color = Color.Transparent,
        interactionSource = interactionSource
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(Color(0xFF00796B), Color(0xFF004D40))
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = iconResId),
                    contentDescription = text,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = text,
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}