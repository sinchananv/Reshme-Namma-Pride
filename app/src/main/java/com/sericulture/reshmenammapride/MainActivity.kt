package com.sericulture.reshmenammapride

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

val RoseGoldDark = Color(0xFF4A1530)
val RoseGoldMid = Color(0xFFB76E79)
val RoseGoldLight = Color(0xFFD4956A)
val RoseGoldPale = Color(0xFFFFE4E1)
val RoseGoldButton = Color(0xFFB76E79)
val RoseGoldCard = Color(0xFF8B3A52)
val CyanAccent = Color(0xFF00BCD4)

class MainActivity : ComponentActivity() {
    private lateinit var textToSpeech: TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textToSpeech = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.language = Locale("kn", "IN")
            }
        }
        setContent {
            ReshmeNammaPrideApp(textToSpeech = textToSpeech)
        }
    }
    override fun onDestroy() {
        textToSpeech.shutdown()
        super.onDestroy()
    }
}

@Composable
fun ReshmeNammaPrideApp(textToSpeech: TextToSpeech) {
    var currentScreen by remember { mutableStateOf("splash") }
    var batchName by remember { mutableStateOf("") }
    var batchDate by remember { mutableStateOf("") }
    var instarStage by remember { mutableStateOf(1) }
    var temperature by remember { mutableStateOf("") }
    var humidity by remember { mutableStateOf("") }
    var adviceText by remember { mutableStateOf("") }
    var kannadaAdvice by remember { mutableStateOf("") }
    var dialColor by remember { mutableStateOf(Color.Green) }

    when (currentScreen) {
        "splash" -> SplashScreen(onNavigate = { currentScreen = it })
        "home" -> HomeScreen(onNavigate = { currentScreen = it })
        "menu" -> MenuScreen(onNavigate = { currentScreen = it })
        "batch" -> BatchScreen(
            batchName = batchName,
            batchDate = batchDate,
            instarStage = instarStage,
            onBatchNameChange = { batchName = it },
            onBatchDateChange = { batchDate = it },
            onInstarChange = { instarStage = it },
            onNavigate = { currentScreen = it }
        )
        "climate" -> ClimateScreen(
            temperature = temperature,
            humidity = humidity,
            instarStage = instarStage,
            onTempChange = { temperature = it },
            onHumidityChange = { humidity = it },
            onAdviceGenerated = { advice, kannada, color ->
                adviceText = advice
                kannadaAdvice = kannada
                dialColor = color
                currentScreen = "advice"
            },
            onNavigate = { currentScreen = it }
        )
        "advice" -> AdviceScreen(
            adviceText = adviceText,
            kannadaAdvice = kannadaAdvice,
            dialColor = dialColor,
            textToSpeech = textToSpeech,
            onNavigate = { currentScreen = it }
        )
        "guide" -> HowToMeasureScreen(onNavigate = { currentScreen = it })
        "cocoon" -> CocoonTimerScreen(
            onNavigate = { currentScreen = it },
            batchDate = batchDate
        )
    }
}

@Composable
fun SplashScreen(onNavigate: (String) -> Unit) {
    var alpha by remember { mutableStateOf(0f) }
    val animatedAlpha by animateFloatAsState(
        targetValue = alpha,
        animationSpec = tween(durationMillis = 1500),
        label = "alpha"
    )
    LaunchedEffect(Unit) {
        alpha = 1f
        kotlinx.coroutines.delay(3000)
        onNavigate("home")
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF2D0A1E),
                        Color(0xFF4A1530),
                        Color(0xFF8B3A52)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.alpha(animatedAlpha)
        ) {
            Text(text = "🐛", fontSize = 100.sp)
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Reshme Namma Pride",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = RoseGoldPale,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "ರೇಷ್ಮೆ ನಮ್ಮ ಹೆಮ್ಮೆ",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = RoseGoldLight,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "🌿 Karnataka's Smart Silk Guardian 🌿",
                fontSize = 14.sp,
                color = RoseGoldMid,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(48.dp))
            CircularProgressIndicator(
                color = RoseGoldMid,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun HomeScreen(onNavigate: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF2D0A1E),
                        Color(0xFF4A1530),
                        Color(0xFF6B2040)
                    )
                )
            )
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(text = "🐛", fontSize = 80.sp, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Reshme Namma Pride",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = RoseGoldPale,
            textAlign = TextAlign.Center
        )
        Text(
            text = "ರೇಷ್ಮೆ ನಮ್ಮ ಹೆಮ್ಮೆ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = RoseGoldLight,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Karnataka's Smart Silk Farming Assistant",
            fontSize = 12.sp,
            color = RoseGoldMid,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = RoseGoldCard),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🏆 Karnataka Silk Facts",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = RoseGoldPale
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "🥇", fontSize = 24.sp)
                        Text(text = "Silk Capital", fontSize = 11.sp, color = RoseGoldLight)
                        Text(text = "of India", fontSize = 11.sp, color = RoseGoldLight)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "👨‍🌾", fontSize = 24.sp)
                        Text(text = "1.5 Lakh", fontSize = 11.sp, color = RoseGoldLight)
                        Text(text = "Farmers", fontSize = 11.sp, color = RoseGoldLight)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "💰", fontSize = 24.sp)
                        Text(text = "₹20,000", fontSize = 11.sp, color = RoseGoldLight)
                        Text(text = "Per Batch", fontSize = 11.sp, color = RoseGoldLight)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { onNavigate("menu") },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RoseGoldButton),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "🌟 Get Started | ಪ್ರಾರಂಭಿಸಿ",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = RoseGoldPale
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Made with ❤️ for Karnataka Silk Farmers",
            fontSize = 11.sp,
            color = RoseGoldMid,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun MenuScreen(onNavigate: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF2D0A1E),
                        Color(0xFF4A1530),
                        Color(0xFF6B2040)
                    )
                )
            )
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "🌿 What would you like to do?",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = RoseGoldPale,
            textAlign = TextAlign.Center
        )
        Text(
            text = "ನೀವು ಏನು ಮಾಡಲು ಬಯಸುತ್ತೀರಿ?",
            fontSize = 16.sp,
            color = RoseGoldLight,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { onNavigate("batch") },
            modifier = Modifier.fillMaxWidth().height(64.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RoseGoldButton),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "🌱 Start New Batch",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = RoseGoldPale
                )
                Text(
                    text = "ಹೊಸ ಬ್ಯಾಚ್ ಪ್ರಾರಂಭಿಸಿ",
                    fontSize = 12.sp,
                    color = RoseGoldPale
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onNavigate("cocoon") },
            modifier = Modifier.fillMaxWidth().height(64.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6D4C41)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "🥚 Cocoon Harvest Timer",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = RoseGoldPale
                )
                Text(
                    text = "ಗೂಡು ಕೊಯ್ಲು ಸಮಯ",
                    fontSize = 12.sp,
                    color = RoseGoldPale
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onNavigate("guide") },
            modifier = Modifier.fillMaxWidth().height(64.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RoseGoldCard),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "📖 How to Measure",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = RoseGoldPale
                )
                Text(
                    text = "ಅಳೆಯುವುದು ಹೇಗೆ",
                    fontSize = 12.sp,
                    color = RoseGoldPale
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        TextButton(onClick = { onNavigate("home") }) {
            Text(text = "← Back to Home | ಮನೆಗೆ ಹಿಂತಿರುಗಿ", color = RoseGoldLight)
        }
    }
}

@Composable
fun BatchScreen(
    batchName: String,
    batchDate: String,
    instarStage: Int,
    onBatchNameChange: (String) -> Unit,
    onBatchDateChange: (String) -> Unit,
    onInstarChange: (Int) -> Unit,
    onNavigate: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF2D0A1E), Color(0xFF4A1530))
                )
            )
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "🌱 Start New Batch",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = RoseGoldPale
        )
        Text(
            text = "ಹೊಸ ಬ್ಯಾಚ್ ಪ್ರಾರಂಭಿಸಿ",
            fontSize = 16.sp,
            color = RoseGoldLight
        )
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = batchName,
            onValueChange = onBatchNameChange,
            label = { Text("Batch Name | ಬ್ಯಾಚ್ ಹೆಸರು", color = RoseGoldLight) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = RoseGoldPale,
                unfocusedTextColor = RoseGoldPale,
                focusedBorderColor = RoseGoldLight,
                unfocusedBorderColor = RoseGoldMid
            )
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = batchDate,
            onValueChange = onBatchDateChange,
            label = { Text("Start Date DD/MM/YYYY | ದಿನಾಂಕ", color = RoseGoldLight) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = RoseGoldPale,
                unfocusedTextColor = RoseGoldPale,
                focusedBorderColor = RoseGoldLight,
                unfocusedBorderColor = RoseGoldMid
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = RoseGoldCard),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🐛 Select Instar Stage | ಹಂತ ಆಯ್ಕೆ ಮಾಡಿ",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = RoseGoldPale,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    listOf(
                        Triple("🥚", 1, "Hatching"),
                        Triple("🐛", 2, "Growing"),
                        Triple("🐛", 3, "Active"),
                        Triple("🌿", 4, "Feeding"),
                        Triple("🫧", 5, "Cocoon")
                    ).forEach { (emoji, stage, label) ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(
                                onClick = { onInstarChange(stage) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (instarStage == stage)
                                        CyanAccent else RoseGoldDark
                                ),
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(8.dp),
                                modifier = Modifier.size(48.dp)
                            ) {
                                Text(text = emoji, fontSize = 20.sp)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "$stage",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (instarStage == stage)
                                    CyanAccent else RoseGoldLight
                            )
                            Text(
                                text = label,
                                fontSize = 9.sp,
                                color = if (instarStage == stage)
                                    CyanAccent else RoseGoldLight,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Selected: Stage $instarStage | ಆಯ್ಕೆ: ಹಂತ $instarStage",
                    fontSize = 13.sp,
                    color = CyanAccent,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { onNavigate("climate") },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RoseGoldButton),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Next → Enter Climate | ಮುಂದೆ",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = RoseGoldPale
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        TextButton(onClick = { onNavigate("menu") }) {
            Text(text = "← Back | ಹಿಂತಿರುಗಿ", color = RoseGoldLight)
        }
    }
}

@Composable
fun InstarGuideCard(instarStage: Int) {
    val tempRange = when (instarStage) {
        1 -> "26°C - 28°C"
        2 -> "26°C - 28°C"
        3 -> "25°C - 27°C"
        4 -> "24°C - 26°C"
        5 -> "23°C - 25°C"
        else -> "25°C - 27°C"
    }
    val humRange = when (instarStage) {
        1 -> "85% - 90%"
        2 -> "80% - 85%"
        3 -> "75% - 80%"
        4 -> "70% - 75%"
        5 -> "65% - 70%"
        else -> "75% - 80%"
    }
    val stageName = when (instarStage) {
        1 -> "Newly Hatched | ಹೊಸದಾಗಿ ಹುಟ್ಟಿದ"
        2 -> "Small Worms | ಚಿಕ್ಕ ಹುಳುಗಳು"
        3 -> "Growing Worms | ಬೆಳೆಯುತ್ತಿರುವ"
        4 -> "Big Worms | ದೊಡ್ಡ ಹುಳುಗಳು"
        5 -> "Ready for Cocoon | ಗೂಡು ಕಟ್ಟಲು ಸಿದ್ಧ"
        else -> ""
    }
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = RoseGoldCard),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "📊 Ideal Range for Stage $instarStage",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = RoseGoldPale,
                textAlign = TextAlign.Center
            )
            Text(
                text = stageName,
                fontSize = 12.sp,
                color = RoseGoldLight,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "🌡️", fontSize = 24.sp)
                    Text(text = "Temperature", fontSize = 11.sp, color = RoseGoldLight)
                    Text(text = "ತಾಪಮಾನ", fontSize = 11.sp, color = RoseGoldLight)
                    Text(
                        text = tempRange,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = CyanAccent
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "💧", fontSize = 24.sp)
                    Text(text = "Humidity", fontSize = 11.sp, color = RoseGoldLight)
                    Text(text = "ಆರ್ದ್ರತೆ", fontSize = 11.sp, color = RoseGoldLight)
                    Text(
                        text = humRange,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = CyanAccent
                    )
                }
            }
        }
    }
}

@Composable
fun ClimateScreen(
    temperature: String,
    humidity: String,
    instarStage: Int,
    onTempChange: (String) -> Unit,
    onHumidityChange: (String) -> Unit,
    onAdviceGenerated: (String, String, Color) -> Unit,
    onNavigate: (String) -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF2D0A1E), Color(0xFF4A1530))
                )
            )
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "🌡️ Climate Entry | ಹವಾಮಾನ ನಮೂದು",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = RoseGoldPale,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Instar Stage | ಹಂತ: $instarStage",
            fontSize = 14.sp,
            color = RoseGoldLight
        )
        Spacer(modifier = Modifier.height(16.dp))
        InstarGuideCard(instarStage = instarStage)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = temperature,
            onValueChange = onTempChange,
            label = { Text("Temperature °C | ತಾಪಮಾನ", color = RoseGoldLight) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = RoseGoldPale,
                unfocusedTextColor = RoseGoldPale,
                focusedBorderColor = RoseGoldLight,
                unfocusedBorderColor = RoseGoldMid
            )
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = humidity,
            onValueChange = onHumidityChange,
            label = { Text("Humidity % | ಆರ್ದ್ರತೆ", color = RoseGoldLight) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = RoseGoldPale,
                unfocusedTextColor = RoseGoldPale,
                focusedBorderColor = RoseGoldLight,
                unfocusedBorderColor = RoseGoldMid
            )
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                val temp = temperature.toFloatOrNull()
                val hum = humidity.toFloatOrNull()
                when {
                    temperature.isEmpty() -> {
                        android.widget.Toast.makeText(
                            context,
                            "⚠️ Please enter Temperature! | ತಾಪಮಾನ ನಮೂದಿಸಿ!",
                            android.widget.Toast.LENGTH_LONG
                        ).show()
                    }
                    humidity.isEmpty() -> {
                        android.widget.Toast.makeText(
                            context,
                            "⚠️ Please enter Humidity! | ಆರ್ದ್ರತೆ ನಮೂದಿಸಿ!",
                            android.widget.Toast.LENGTH_LONG
                        ).show()
                    }
                    temp == null -> {
                        android.widget.Toast.makeText(
                            context,
                            "⚠️ Enter valid number for Temperature!",
                            android.widget.Toast.LENGTH_LONG
                        ).show()
                    }
                    hum == null -> {
                        android.widget.Toast.makeText(
                            context,
                            "⚠️ Enter valid number for Humidity!",
                            android.widget.Toast.LENGTH_LONG
                        ).show()
                    }
                    else -> {
                        val result = getAdvice(temp, hum, instarStage)
                        onAdviceGenerated(result.first, result.second, result.third)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RoseGoldButton),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Get Smart Advice | ಸಲಹೆ ಪಡೆಯಿರಿ →",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = RoseGoldPale
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        TextButton(onClick = { onNavigate("batch") }) {
            Text(text = "← Back | ಹಿಂತಿರುಗಿ", color = RoseGoldLight)
        }
    }
}

@Composable
fun AdviceScreen(
    adviceText: String,
    kannadaAdvice: String,
    dialColor: Color,
    textToSpeech: TextToSpeech,
    onNavigate: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF2D0A1E), Color(0xFF4A1530))
                )
            )
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🎯 Smart Advice | ಸ್ಮಾರ್ಟ್ ಸಲಹೆ",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = RoseGoldPale,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        Box(
            modifier = Modifier
                .size(160.dp)
                .background(dialColor, shape = RoundedCornerShape(80.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = when (dialColor) {
                    Color.Green -> "✅\nSAFE\nಸುರಕ್ಷಿತ"
                    Color(0xFFFFA500) -> "⚠️\nCAUTION\nಎಚ್ಚರಿಕೆ"
                    else -> "🚨\nDANGER\nಅಪಾಯ"
                },
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = RoseGoldCard),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = adviceText,
                    fontSize = 14.sp,
                    color = RoseGoldPale,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = RoseGoldMid, thickness = 1.dp)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = kannadaAdvice,
                    fontSize = 14.sp,
                    color = RoseGoldLight,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                textToSpeech.speak(
                    kannadaAdvice,
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    null
                )
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RoseGoldCard),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "🔊 Listen in Kannada | ಕನ್ನಡದಲ್ಲಿ ಕೇಳಿ",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = RoseGoldPale
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = { onNavigate("climate") },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RoseGoldButton),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Enter New Reading | ಹೊಸ ದಾಖಲೆ",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = RoseGoldPale
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        TextButton(onClick = { onNavigate("home") }) {
            Text(text = "🏠 Back to Home | ಮನೆಗೆ ಹಿಂತಿರುಗಿ", color = RoseGoldLight)
        }
    }
}

@Composable
fun HowToMeasureScreen(onNavigate: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF2D0A1E), Color(0xFF4A1530))
                )
            )
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "📖 How to Measure",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = RoseGoldPale
        )
        Text(
            text = "ಅಳೆಯುವುದು ಹೇಗೆ",
            fontSize = 18.sp,
            color = RoseGoldLight
        )
        Spacer(modifier = Modifier.height(24.dp))
        GuideCard(
            emoji = "🌡️",
            title = "Temperature | ತಾಪಮಾನ",
            steps = "1. Buy a wall thermometer (₹50-100)\n" +
                    "2. Hang it in middle of rearing house\n" +
                    "3. Read the number shown\n" +
                    "4. Enter that number in app\n\n" +
                    "ಗೋಡೆ ಥರ್ಮಾಮೀಟರ್ ತರ್ರಿ ಮತ್ತು ಮನೆಯ ಮಧ್ಯದಲ್ಲಿ ನೇಲಿಸಿ"
        )
        Spacer(modifier = Modifier.height(16.dp))
        GuideCard(
            emoji = "💧",
            title = "Humidity | ಆರ್ದ್ರತೆ",
            steps = "1. Buy a hygrometer (₹100-200)\n" +
                    "2. Place it near the silkworms\n" +
                    "3. Read the % number shown\n" +
                    "4. Enter that number in app\n\n" +
                    "ಹೈಗ್ರೋಮೀಟರ್ ತರ್ರಿ ಮತ್ತು ರೇಷ್ಮೆ ಹುಳುಗಳ ಬಳಿ ಇಡಿ"
        )
        Spacer(modifier = Modifier.height(16.dp))
        GuideCard(
            emoji = "⏰",
            title = "When to Measure | ಯಾವಾಗ ಅಳೆಯಬೇಕು",
            steps = "Morning: 6 AM - 8 AM\n" +
                    "Afternoon: 12 PM - 2 PM\n" +
                    "Evening: 6 PM - 8 PM\n\n" +
                    "ಬೆಳಿಗ್ಗೆ, ಮಧ್ಯಾಹ್ನ ಮತ್ತು ಸಂಜೆ ಅಳೆಯಿರಿ"
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextButton(onClick = { onNavigate("menu") }) {
            Text(
                text = "← Back | ಹಿಂತಿರುಗಿ",
                color = RoseGoldLight
            )
        }
    }
}

@Composable
fun GuideCard(emoji: String, title: String, steps: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = RoseGoldCard),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "$emoji $title",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = RoseGoldPale
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = steps,
                fontSize = 13.sp,
                color = RoseGoldLight,
                lineHeight = 22.sp
            )
        }
    }
}

@Composable
fun CocoonTimerScreen(
    onNavigate: (String) -> Unit,
    batchDate: String
) {
    val context = LocalContext.current
    var inputDate by remember { mutableStateOf(batchDate) }
    var timerResult by remember { mutableStateOf("") }
    var daysRemaining by remember { mutableStateOf(0) }
    var isReady by remember { mutableStateOf(false) }
    var harvestDateStr by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF2D0A1E), Color(0xFF4A1530))
                )
            )
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "🥚 Cocoon Harvest Timer",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = RoseGoldPale,
            textAlign = TextAlign.Center
        )
        Text(
            text = "ಗೂಡು ಕೊಯ್ಲು ಸಮಯ",
            fontSize = 16.sp,
            color = RoseGoldLight,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Silkworms are ready for harvest after 26 days!",
            fontSize = 12.sp,
            color = RoseGoldMid,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            value = inputDate,
            onValueChange = { inputDate = it },
            label = { Text("Batch Start Date DD/MM/YYYY | ದಿನಾಂಕ", color = RoseGoldLight) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = RoseGoldPale,
                unfocusedTextColor = RoseGoldPale,
                focusedBorderColor = RoseGoldLight,
                unfocusedBorderColor = RoseGoldMid
            )
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                if (inputDate.isEmpty()) {
                    android.widget.Toast.makeText(
                        context,
                        "⚠️ Please enter batch start date!",
                        android.widget.Toast.LENGTH_LONG
                    ).show()
                } else {
                    try {
                        val parts = inputDate.trim().split("/")
                        if (parts.size != 3) throw Exception("Invalid format")
                        val day = parts[0].trim().toInt()
                        val month = parts[1].trim().toInt() - 1
                        var year = parts[2].trim().toInt()
                        if (year < 100) year += 2000

                        val startCal = java.util.Calendar.getInstance()
                        startCal.set(year, month, day, 0, 0, 0)
                        startCal.set(java.util.Calendar.MILLISECOND, 0)

                        val harvestCal = startCal.clone() as java.util.Calendar
                        harvestCal.add(java.util.Calendar.DAY_OF_MONTH, 26)

                        val today = java.util.Calendar.getInstance()
                        today.set(java.util.Calendar.HOUR_OF_DAY, 0)
                        today.set(java.util.Calendar.MINUTE, 0)
                        today.set(java.util.Calendar.SECOND, 0)
                        today.set(java.util.Calendar.MILLISECOND, 0)

                        val diffMillis = harvestCal.timeInMillis - today.timeInMillis
                        val days = (diffMillis / (1000 * 60 * 60 * 24)).toInt()

                        val hDay = harvestCal.get(java.util.Calendar.DAY_OF_MONTH)
                        val hMonth = harvestCal.get(java.util.Calendar.MONTH) + 1
                        val hYear = harvestCal.get(java.util.Calendar.YEAR)
                        harvestDateStr = "$hDay/$hMonth/$hYear"

                        daysRemaining = maxOf(days, 0)
                        isReady = days <= 0

                        timerResult = if (isReady) {
                            "🎉 Your silkworms are READY!\nTransfer to spinning trays NOW!\n\nನಿಮ್ಮ ರೇಷ್ಮೆ ಹುಳುಗಳು ಕೊಯ್ಲಿಗೆ ಸಿದ್ಧ!\nತಕ್ಷಣ ನೂಲುವ ತಟ್ಟೆಗೆ ವರ್ಗಾಯಿಸಿ!"
                        } else {
                            "Harvest Date: $harvestDateStr\nಕೊಯ್ಲು ದಿನಾಂಕ: $harvestDateStr"
                        }
                    } catch (e: Exception) {
                        android.widget.Toast.makeText(
                            context,
                            "⚠️ Invalid date! Please use DD/MM/YYYY format",
                            android.widget.Toast.LENGTH_LONG
                        ).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RoseGoldButton),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Check Harvest Time | ಕೊಯ್ಲು ಸಮಯ ತಿಳಿಯಿರಿ",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = RoseGoldPale
            )
        }
        if (timerResult.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (isReady) Color(0xFF1B5E20) else RoseGoldCard
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (isReady) {
                        Text(
                            text = "🎉 HARVEST TIME!",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "ಕೊಯ್ಲು ಸಮಯ!",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    } else {
                        Text(
                            text = "⏰ Days Remaining | ಉಳಿದ ದಿನಗಳು",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = RoseGoldPale,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "$daysRemaining",
                            fontSize = 72.sp,
                            fontWeight = FontWeight.Bold,
                            color = CyanAccent,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "days | ದಿನಗಳು",
                            fontSize = 16.sp,
                            color = RoseGoldLight,
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = timerResult,
                        fontSize = 14.sp,
                        color = if (isReady) Color.White else RoseGoldPale,
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        TextButton(onClick = { onNavigate("menu") }) {
            Text(
                text = "← Back | ಹಿಂತಿರುಗಿ",
                color = RoseGoldLight
            )
        }
    }
}

fun getAdvice(temp: Float, hum: Float, instar: Int): Triple<String, String, Color> {
    val idealTemp = when (instar) {
        1 -> Pair(26f, 28f)
        2 -> Pair(26f, 28f)
        3 -> Pair(25f, 27f)
        4 -> Pair(24f, 26f)
        5 -> Pair(23f, 25f)
        else -> Pair(25f, 27f)
    }
    val idealHum = when (instar) {
        1 -> Pair(85f, 90f)
        2 -> Pair(80f, 85f)
        3 -> Pair(75f, 80f)
        4 -> Pair(70f, 75f)
        5 -> Pair(65f, 70f)
        else -> Pair(75f, 80f)
    }
    val tempLow = idealTemp.first
    val tempHigh = idealTemp.second
    val humLow = idealHum.first
    val humHigh = idealHum.second

    return when {
        temp < 10f || temp > 45f -> Triple(
            "🚨 DANGER! Temperature ${temp}°C is critically out of range!\n\nThis will KILL your silkworms!\nImmediate action needed!\nValid range: 10°C to 45°C",
            "🚨 ಅಪಾಯ! ತಾಪಮಾನ ${temp}°C ತುಂಬಾ ಅಪಾಯಕಾರಿ!\n\nಇದು ನಿಮ್ಮ ರೇಷ್ಮೆ ಹುಳುಗಳನ್ನು ಕೊಲ್ಲುತ್ತದೆ!\nತಕ್ಷಣ ಕ್ರಮ ತೆಗೆದುಕೊಳ್ಳಿ!",
            Color.Red
        )
        hum < 50f || hum > 95f -> Triple(
            "🚨 DANGER! Humidity ${hum}% is critically out of range!\n\nThis will KILL your silkworms!\nImmediate action needed!\nValid range: 50% to 95%",
            "🚨 ಅಪಾಯ! ಆರ್ದ್ರತೆ ${hum}% ತುಂಬಾ ಅಪಾಯಕಾರಿ!\n\nಇದು ನಿಮ್ಮ ರೇಷ್ಮೆ ಹುಳುಗಳನ್ನು ಕೊಲ್ಲುತ್ತದೆ!\nತಕ್ಷಣ ಕ್ರಮ ತೆಗೆದುಕೊಳ್ಳಿ!",
            Color.Red
        )
        temp in tempLow..tempHigh && hum in humLow..humHigh -> Triple(
            "✅ SAFE! Perfect conditions for Instar $instar!\n\nTemperature: ${temp}°C ✅\nHumidity: ${hum}% ✅\n\nYour silkworms are happy and growing well!\nKeep maintaining these conditions!",
            "✅ ಸುರಕ್ಷಿತ! ಹಂತ $instar ಗೆ ಪರಿಪೂರ್ಣ ಪರಿಸ್ಥಿತಿ!\n\nತಾಪಮಾನ: ${temp}°C ✅\nಆರ್ದ್ರತೆ: ${hum}% ✅\n\nನಿಮ್ಮ ರೇಷ್ಮೆ ಹುಳುಗಳು ಚೆನ್ನಾಗಿ ಬೆಳೆಯುತ್ತಿವೆ!",
            Color.Green
        )
        temp > tempHigh + 2f -> Triple(
            "⚠️ CAUTION! Temperature ${temp}°C TOO HIGH for Instar $instar!\n\n🔴 Open all windows immediately\n🔴 Spread wet gunny bags on floor\n🔴 Spray water on walls\n\nTarget: ${tempLow}°C to ${tempHigh}°C",
            "⚠️ ಎಚ್ಚರಿಕೆ! ತಾಪಮಾನ ${temp}°C ಹಂತ $instar ಗೆ ತುಂಬಾ ಹೆಚ್ಚು!\n\n🔴 ಎಲ್ಲಾ ಕಿಟಕಿಗಳನ್ನು ತೆರೆಯಿರಿ\n🔴 ಒದ್ದೆ ಗೋಣಿ ಚೀಲ ಹಾಕಿ\n🔴 ಗೋಡೆಗೆ ನೀರು ಹಾಕಿ",
            Color(0xFFFFA500)
        )
        temp < tempLow - 2f -> Triple(
            "⚠️ CAUTION! Temperature ${temp}°C TOO LOW for Instar $instar!\n\n🔵 Close all windows immediately\n🔵 Use heaters if available\n🔵 Cover trays with newspaper\n\nTarget: ${tempLow}°C to ${tempHigh}°C",
            "⚠️ ಎಚ್ಚರಿಕೆ! ತಾಪಮಾನ ${temp}°C ಹಂತ $instar ಗೆ ತುಂಬಾ ಕಡಿಮೆ!\n\n🔵 ಎಲ್ಲಾ ಕಿಟಕಿಗಳನ್ನು ಮುಚ್ಚಿರಿ\n🔵 ಹೀಟರ್ ಬಳಸಿ\n🔵 ತಟ್ಟೆಗಳನ್ನು ಪೇಪರ್‌ನಿಂದ ಮುಚ್ಚಿ",
            Color(0xFFFFA500)
        )
        hum > humHigh + 5f -> Triple(
            "⚠️ CAUTION! Humidity ${hum}% TOO HIGH for Instar $instar!\n\n🔴 Open windows for ventilation\n🔴 Remove wet materials from house\n🔴 Use dry leaves under silkworms\n\nTarget: ${humLow}% to ${humHigh}%",
            "⚠️ ಎಚ್ಚರಿಕೆ! ಆರ್ದ್ರತೆ ${hum}% ಹಂತ $instar ಗೆ ತುಂಬಾ ಹೆಚ್ಚು!\n\n🔴 ಗಾಳಿಗಾಗಿ ಕಿಟಕಿ ತೆರೆಯಿರಿ\n🔴 ಒದ್ದೆ ವಸ್ತುಗಳನ್ನು ತೆಗೆಯಿರಿ\n🔴 ಒಣ ಎಲೆ ಬಳಸಿ",
            Color(0xFFFFA500)
        )
        hum < humLow - 5f -> Triple(
            "⚠️ CAUTION! Humidity ${hum}% TOO LOW for Instar $instar!\n\n🔵 Spray water around rearing house\n🔵 Place wet gunny bags near silkworms\n🔵 Close windows partially\n\nTarget: ${humLow}% to ${humHigh}%",
            "⚠️ ಎಚ್ಚರಿಕೆ! ಆರ್ದ್ರತೆ ${hum}% ಹಂತ $instar ಗೆ ತುಂಬಾ ಕಡಿಮೆ!\n\n🔵 ಮನೆಯ ಸುತ್ತ ನೀರು ಹಾಕಿ\n🔵 ಒದ್ದೆ ಗೋಣಿ ಚೀಲ ಇಡಿ\n🔵 ಕಿಟಕಿ ಅರ್ಧ ಮುಚ್ಚಿ",
            Color(0xFFFFA500)
        )
        else -> Triple(
            "⚠️ CAUTION! Conditions slightly off for Instar $instar!\n\nTemperature: ${temp}°C\nHumidity: ${hum}%\n\nMonitor closely and adjust!\nTarget: ${tempLow}°C-${tempHigh}°C and ${humLow}%-${humHigh}%",
            "⚠️ ಎಚ್ಚರಿಕೆ! ಹಂತ $instar ಗೆ ಪರಿಸ್ಥಿತಿ ಸ್ವಲ್ಪ ಸರಿಯಿಲ್ಲ!\n\nತಾಪಮಾನ: ${temp}°C\nಆರ್ದ್ರತೆ: ${hum}%\n\nಗಮನವಿಟ್ಟು ನೋಡಿ!",
            Color(0xFFFFA500)
        )
    }
}