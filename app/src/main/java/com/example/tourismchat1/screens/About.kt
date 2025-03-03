package com.example.tourismchat1.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tourismchat1.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun About(navController: NavHostController) {
    val scrollState = rememberScrollState()

    // Expandable state variables
    var isExpandedGeography by remember { mutableStateOf(false) }
    var isExpandedSeasons by remember { mutableStateOf(false) }
    var isExpandedPeopleLife by remember { mutableStateOf(false) }
    var isExpandedOther by remember { mutableStateOf(false) }

    val aboutText = """
        The present district of Bhojpur came into existence in 1972. Earlier this district was part of old Shahabad district...
        Ara town is the headquarters of the district and also its principal town.
    """.trimIndent()

    val geographyText = """
        Bhojpur is located in western Bihar and is surrounded by various districts. 
        The Sone River is an important geographical feature of the district.
    """.trimIndent()

    val seasonsText = """
        Bhojpur experiences three major seasons: summer, monsoon, and winter.
        Summers are hot, monsoons bring moderate to heavy rainfall, and winters are pleasant.
    """.trimIndent()

    val peopleLifeText = """
        The people of Bhojpur are known for their rich cultural heritage.
        Festivals like Chhath Puja and Diwali are celebrated with great enthusiasm.
    """.trimIndent()

    val otherText = """
        Bhojpur has important landmarks including Jagdishpur Fort, historic temples, and Veer Kunwar Singh University.
    """.trimIndent()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bhojpur District Tourism", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color(0xFF0D47A1))
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Image of Bhojpur
            Image(
                painter = painterResource(id = R.drawable.bhojpur_image),
                contentDescription = "Bhojpur District",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // About Bhojpur - Always Visible
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "About Bhojpur",
                        fontSize = 18.sp,
                        color = Color(0xFF0D47A1)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = aboutText, fontSize = 14.sp, color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Expandable Sections
            ExpandableSection(
                title = "Geography",
                text = geographyText,
                isExpanded = isExpandedGeography,
                onToggle = { isExpandedGeography = !isExpandedGeography }
            )

            ExpandableSection(
                title = "Seasons",
                text = seasonsText,
                isExpanded = isExpandedSeasons,
                onToggle = { isExpandedSeasons = !isExpandedSeasons }
            )

            ExpandableSection(
                title = "People & Life",
                text = peopleLifeText,
                isExpanded = isExpandedPeopleLife,
                onToggle = { isExpandedPeopleLife = !isExpandedPeopleLife }
            )

            ExpandableSection(
                title = "Other Important Things",
                text = otherText,
                isExpanded = isExpandedOther,
                onToggle = { isExpandedOther = !isExpandedOther }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// Reusable Expandable Section Composable with Expand Icon
@Composable
fun ExpandableSection(title: String, text: String, isExpanded: Boolean, onToggle: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    color = Color(0xFF0D47A1),
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onToggle) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = if (isExpanded) "Collapse" else "Expand"
                    )
                }
            }

            if (isExpanded) {
                Text(text = text, fontSize = 14.sp, color = Color.Black)
            }
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
}
