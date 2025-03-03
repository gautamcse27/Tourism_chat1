package com.example.tourismchat1.screens
import com.example.tourismchat1.components.ImageSlider
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.tourismchat1.R
import com.example.tourismchat1.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.tourismchat1.data.models.TouristPlace
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun AttractionDetailsScreen(navController: NavHostController, id: String?) {
    var attraction by remember { mutableStateOf<TouristPlace?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    LaunchedEffect(id) {
        id?.toIntOrNull()?.let { intId ->
            withContext(Dispatchers.IO) {
                try {
                    val response = RetrofitInstance.api.getAttractionDetails(intId)
                    attraction = response
                    isLoading = false
                } catch (e: Exception) {
                    errorMessage = "Error fetching details: ${e.message}"
                    isLoading = false
                }
            }
        } ?: run {
            errorMessage = "Invalid attraction ID"
            isLoading = false
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            errorMessage != null -> {
                Text(
                    text = errorMessage ?: "",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
            attraction != null -> {
                Box(modifier = Modifier.fillMaxWidth()) {
                    attraction?.image_urls?.let { images ->
                        if (images.isNotEmpty()) {
                            ImageSlider(imageUrls = images)
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }

                        IconButton(
                            onClick = { shareAttraction(context, attraction) },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_share_24),
                                contentDescription = "Share",
                                tint = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = attraction?.name ?: "",
                    fontSize = 24.sp,
                    color = Color.Blue,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Text(
                    text = "Location: ${attraction?.location}",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = attraction?.description ?: "",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}


// Function to Share Attraction Details
private fun shareAttraction(context: Context, attraction: TouristPlace?) {
    attraction?.let {
        val shareText = "${it.name}\nLocation: ${it.location}\n\n${it.description}"
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        context.startActivity(Intent.createChooser(intent, "Share via"))
    }
}
