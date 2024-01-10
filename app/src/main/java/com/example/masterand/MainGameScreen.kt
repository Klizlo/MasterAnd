package com.example.masterand

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import java.util.Collections

@Composable
@Preview
fun GameMainScreenPreview() {

    val navController = rememberNavController()

    GameMainScreen(navController = navController, number = 4)
}

val gameColors = listOf(
    Color.Red,
    Color.Blue,
    Color.Cyan,
    Color.Green,
    Color.Gray,
    Color.Magenta,
    Color.Yellow
)

@Composable
fun GameMainScreen(navController: NavHostController, number: Int?) {

    val selectedColorList = remember { mutableStateOf<List<List<Color>>>(listOf()) }
    val feedbackColorList = remember { mutableStateOf<List<List<Color>>>(listOf()) }

    val availableColorList = remember {
        mutableStateOf(gameColors.shuffled().take(number ?: 4))
    }

    val correctColorList = remember {
        mutableStateOf(selectRandomColors(availableColorList.value))
    }

    val attempts = remember { mutableStateOf(1) }

    val rows = remember {
        mutableStateOf(1)
    }

    val clickable = remember {
        mutableStateOf(true)
    }

    val showStartOverButton = remember {
        mutableStateOf(false)
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScoreText(attempts = attempts.value)
        LazyColumn(
            modifier = Modifier
                .weight(2f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(rows.value.coerceAtLeast(1)) { rowIndex ->
                // bierze listę z kolorami dla danego wiersza
                // jeśli lista jest null wtedy (?:) przypisuje listę z 4 białymi kolorami
                val selectedColors = selectedColorList.value.getOrNull(rowIndex) ?: List(4) { Color.White }
                val feedbackColors = feedbackColorList.value.getOrNull(rowIndex) ?: List(4) { Color.White }

                GameRow(
                    chosenColors = selectedColors,
                    feedbackColors = feedbackColors,
                    isClicked = clickable.value,
                    onCheckClick = {

                        //sprawdzamy czy gracz wybrał w tym ruchu jakieś kolory czy pozostawił białe przyciski
                        if (selectedColorList.value.size <= rowIndex) {
                            selectedColorList.value = selectedColorList.value.toMutableList().apply {
                                add(List(4) {Color.White} )
                            }
                        }

                        // Sprawdzamy czy numer wiersza odpowiada liczbie prób i czy przycisk jest klikalny
                        if (rowIndex + 1 == attempts.value && clickable.value){
                            //jeśli tak to
                            feedbackColorList.value = feedbackColorList.value.toMutableList().apply {
                                // sprawdzamy czy rozmiar listy kolorów z informacją zwrotną odpowiada liczbie wierszy
                                // jeśli nie dodajemy listę 4 białych kolorów
                                if (size <= rowIndex) add(List(4) { Color.White})

                                //sprawdzamy czy użytkownik dobrze podał kolory
                                this[rowIndex] = checkColors(
                                    chosenColors = selectedColors,
                                    correctColors = correctColorList.value,
                                    notFoundColor = Color.White
                                )
                            }

                            //zwiększamy liczbę prób
                            attempts.value++

                            //sprawdzamy czy użytkownik wygrał
                            val isWinner = feedbackColorList.value.last().all { it == Color.Red }

                            if(isWinner){
                                // jeśli tak to
                                showStartOverButton.value = true
                            } else {
                                // w przeciwnym wypadku
                                rows.value++
                            }
                        }
                    },
                    onSelectColorClick = { buttonId ->
                        // Sprawdzamy czy numer wiersza odpowiada liczbie prób i czy przycisk jest klikalny
                        if (rowIndex + 1 == attempts.value && clickable.value) {
                            // jeśli tak to
                            selectedColorList.value = selectedColorList.value.toMutableList().apply {
                                // sprawdzamy czy rozmiar listy wybranych kolorów odpowiada liczbie wierszy
                                // jeśli nie dodajemy listę 4 białych kolorów
                                if (size <= rowIndex) add(List(4) {Color.White})
                                
                                // ustawiamy kolory w tym wierszu
                                this[rowIndex] = selectNextAvailableColor(
                                    availableColors = availableColorList.value,
                                    chosenColors = selectedColors,
                                    buttonId = buttonId
                                )
                            }
                        }
                    }
                )
            }
        }
        if(showStartOverButton.value){
            StartOverButton(onClick = {
                selectedColorList.value = emptyList()
                feedbackColorList.value = emptyList()
                attempts.value = 1
                clickable.value = true
                showStartOverButton.value = false
                rows.value = 1
                availableColorList.value = gameColors.shuffled().take(number ?: 4)
                correctColorList.value = selectRandomColors(availableColorList.value)
            })
        }
        BackButton(navController = navController)
    }
}

@Composable
fun BackButton(navController: NavHostController) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Back")
        }
    }
}

@Composable
fun StartOverButton(onClick: () -> Unit){
    Button(modifier = Modifier.padding(20.dp), onClick = onClick) {
        Text(text = "Start over")
    }
}

@Composable
fun ScoreText(attempts: Int) {
    Text(
        text = "You score: $attempts",
        fontSize = 36.sp,
        modifier = Modifier
            .padding(bottom = 20.dp)
    )
}

@Composable
fun CircularButton(onClick: () -> Unit, color: Color) {
    Button(modifier = Modifier
        .size(50.dp)
        .background(color = MaterialTheme.colorScheme.background),
        border = BorderStroke(width = 2.dp, MaterialTheme.colorScheme.outline),
        colors = ButtonDefaults.buttonColors(containerColor = color,
            contentColor = MaterialTheme.colorScheme.onBackground),
        onClick = { onClick() }) {

    }
}

@Composable
fun SelectableColorRow (colors: List<Color>, onClick: (Int) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
        colors.forEachIndexed {
            index, color -> CircularButton(onClick = { onClick(index) }, color = color)
        }
    }
}

@Composable
fun SmallCircle(color: Color) {
    Box(modifier = Modifier
        .size(20.dp)
        .clip(CircleShape)
        .background(color)
        .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape))
}

@Composable
fun FeedbackCircles(colors: List<Color>) {

    val rows = 2
    val columns = 2

    Column (
        modifier = Modifier.width(80.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        repeat(rows) {
            rowIndex -> Row (
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.width(80.dp)
            ) {
                repeat(columns) {
                    colIndex ->
                        val index = rowIndex * columns + colIndex
                        SmallCircle(color = colors.getOrElse(index) {Color.White})
                }
            }
        }
    }
}

@Composable
fun GameRow(chosenColors: List<Color>,
            feedbackColors: List<Color>,
            isClicked: Boolean,
            onSelectColorClick: (Int) -> Unit,
            onCheckClick: () -> Unit) {
    Row {
        SelectableColorRow(colors = chosenColors,
            onClick = onSelectColorClick)
        CheckButton(enabled = isClicked, onCheckClick = onCheckClick)
        FeedbackCircles(colors = feedbackColors)
    }
}

@Composable
fun CheckButton(enabled: Boolean, onCheckClick: () -> Unit) {
    IconButton(
        modifier = Modifier
            .clip(CircleShape)
            .size(50.dp),
        colors = IconButtonDefaults.filledIconButtonColors(),
        enabled = enabled,
        onClick = onCheckClick
    ) {
        Icon(imageVector = Icons.Filled.Check, contentDescription = "Check Button")
    }
}

fun selectNextAvailableColor(availableColors: List<Color>, chosenColors: List<Color>,
                             buttonId: Int): List<Color> {
    if ( availableColors.any { it !in chosenColors } ) {
        val nextColor = availableColors.first {it !in chosenColors}
        // przesuwamy listę o 1 w lewo aby możliwe było wybranie każdego koloru
        Collections.rotate(availableColors, -1)
        return chosenColors.toMutableList().also { it[buttonId] = nextColor }
    }

    return chosenColors.toMutableList().also { it[buttonId] = Color.White }
}

fun selectRandomColors(availableColors: List<Color>) : List<Color> =
    availableColors.shuffled().take(4)

// not found color = background color
fun checkColors(chosenColors: List<Color>, correctColors: List<Color>,
                notFoundColor: Color) : List<Color> {
    val feedbackColors = mutableListOf<Color>()

    for (i in chosenColors.indices) {
        if (chosenColors[i] == correctColors[i]) {
            feedbackColors.add(Color.Red)
        } else if (correctColors.contains(chosenColors[i])) {
            feedbackColors.add(Color.Yellow)
        } else {
            feedbackColors.add(notFoundColor)
        }
    }

    return feedbackColors
}