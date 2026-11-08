package co.za.dylanwebb.budgetoptimiserfe.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Category(
    val categoryName: String,
    val budget: Double,
    val costType: String,
    val id: Long
)

// Initialise Ktor client for this view
private val httpClient = HttpClient(CIO) {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true // <-- This must be present!
            isLenient = true
            coerceInputValues = true
        })
    }
}

@Composable
@Preview
fun MainScreen() {
    var summary by remember { mutableStateOf(emptyList<Category>()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Trigger GET request when the UI enters composition
    LaunchedEffect(Unit) {
        try {
            // Replace with your actual local or remote backend endpoint
            summary = httpClient.get("http://localhost:8080/api/category").body<List<Category>>()
        } catch (e: Exception) {
            errorMessage = e.localizedMessage ?: "Failed to connect to backend"
        } finally {
            isLoading = false
        }
    }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text("Budget Optimiser", style = MaterialTheme.typography.h4)
                Spacer(modifier = Modifier.height(16.dp))

                when {
                    isLoading -> {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Fetching data from backend...")
                    }
                    errorMessage != null -> {
                        Text("Error: $errorMessage", color = MaterialTheme.colors.error)
                    }
                    summary != null -> {
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Total Budget: $summary", style = MaterialTheme.typography.h6)
                            }
                        }
                    }
                }
            }
        }
    }
}