package com.example.forojogobonito.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.forojogobonito.R

data class Partido(val local: String, val visitante: String, val hora: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartidosScreen() {
    val partidos = remember {
        listOf(
            Partido("Arsenal", "Liverpool", "14:00"),
            Partido("Barcelona", "Real Madrid", "16:30"),
            Partido("Chelsea", "Manchester City", "13:45"),
            Partido("PSG", "Bayern München", "15:15"),
            Partido("AC Milan", "Inter", "17:00"),
            Partido("Borussia Dortmund", "RB Leipzig", "12:00"),
            Partido("Atlético Madrid", "Sevilla", "18:20"),
            Partido("Benfica", "Porto", "11:30")
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Partidos de hoy", style = MaterialTheme.typography.headlineMedium) },
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_vs),
                        contentDescription = "VS",
                        modifier = Modifier.size(28.dp),
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(partidos) { p ->
                ElevatedCard {
                    Column(Modifier.padding(14.dp)) {
                        Text(
                            text = "${p.local}  vs  ${p.visitante}",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = "Hora: ${p.hora}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
