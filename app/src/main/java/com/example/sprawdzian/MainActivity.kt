package com.example.sprawdzian

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sprawdzian.ui.theme.SprawdzianTheme

// --- Definicja kolorów z designu ---
val DarkBackground = Color(0xFF1E2019) // Ciemne tło
val CardBackground = Color(0xFF2C2E25)

val DescCardBackground = Color(0xFF474A3C)
val AccentGreen = Color(0xFFCDBD65)    // Kolor akcentów (zielono-żółty)
val TextWhite = Color(0xFFEEEEEE)      // Biały tekst

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                RecipeScreen()
            }
        }
    }
}

@Composable
fun RecipeScreen() {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // Główny kontener
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp)
            .verticalScroll(scrollState) // Umożliwia przewijanie ekranu
    ) {
        // 1. Tytuł
        Text(
            text = "Classic Vanilla Cake",
            color = AccentGreen,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 2. Zdjęcie Główne
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .padding(bottom = 24.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.vanilla_cake),
                contentDescription = "Vanilla Cake",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // 3. Wiersz z informacjami (Prep, Cook, Serves, Level)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            InfoItem(icon = painterResource(R.drawable.ic_time), label = "Prep", value = "15 min")
            InfoItem(icon = painterResource(R.drawable.ic_cook), label = "Cook", value = "30 min")
            InfoItem(icon = painterResource(R.drawable.ic_people), label = "Serves", value = "4")
            InfoItem(icon = painterResource(R.drawable.ic_level), label = "Level", value = "Easy")
        }

        // 4. Sekcja Opisu
        ContainerBox(title = "Description", icon = Icons.Default.Info) {
            Text(
                text = "A moist and fluffy vanilla cake perfect for any celebration. This classic recipe never fails to impress!",
                color = TextWhite,
                lineHeight = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 5. Sekcja Składników
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = AccentGreen)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Ingredients", color = TextWhite, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        // Lista składników
        val ingredients = listOf(
            "All-purpose flour" to "2 cups",
            "Sugar" to "1 cup",
            "Butter" to "1/2 cup",
            "Eggs" to "3 large",
            "Vanilla extract" to "2 tsp",
            "Baking powder" to "1 tsp"
        )

        ingredients.forEach { (name, amount) ->
            IngredientRow(name, amount)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 6. Adjust Servings
        Text(
            text = "Adjust Servings",
            color = TextWhite,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = "4",
            onValueChange = {},
            label = { Text("Number of servings") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = TextWhite) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AccentGreen,
                unfocusedBorderColor = Color.Gray,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                focusedLabelColor = AccentGreen,
                unfocusedLabelColor = Color.Gray
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(5.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 7. Przyciski na dole
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Przycisk Start Timer
            Button(
                onClick = {
                    Toast.makeText(context, "Timer started!", Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(containerColor = AccentGreen),
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {
                Icon(Icons.Default.DateRange, contentDescription = null, tint = DarkBackground)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Start Timer", color = DarkBackground, fontWeight = FontWeight.Bold)
            }

            // Przycisk Save
            OutlinedButton(
                onClick = {
                    Toast.makeText(context, "Saved to favourites", Toast.LENGTH_SHORT).show()
                },
                border = BorderStroke(1.dp, AccentGreen),
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {
                Icon(Icons.Default.Favorite, contentDescription = null, tint = AccentGreen)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Save", color = AccentGreen, fontWeight = FontWeight.Bold)
            }
        }

        // Dodatkowy odstęp na dole, żeby wygodnie scrollować
        Spacer(modifier = Modifier.height(32.dp))
    }
}

// --- Komponenty pomocnicze (Reusable Components) ---

@Composable
fun InfoItem(icon: Painter, label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = icon,
            contentDescription = label,
            tint = AccentGreen,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, color = Color.Gray, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(13.dp))
        Text(text = value, color = TextWhite, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ContainerBox(title: String, icon: ImageVector, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DescCardBackground, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = TextWhite, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = title, color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        content()
    }
}

@Composable
fun IngredientRow(name: String, amount: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(CardBackground, RoundedCornerShape(8.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Check, contentDescription = null, tint = AccentGreen, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = name, color = TextWhite)
        }
        Text(text = amount, color = AccentGreen, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipe() {
    SprawdzianTheme {
        RecipeScreen()
    }
}
