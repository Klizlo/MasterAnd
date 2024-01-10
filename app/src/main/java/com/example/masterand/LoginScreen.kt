package com.example.masterand

import android.net.Uri
import android.util.Patterns.EMAIL_ADDRESS
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage

@Composable
fun LoginScreen(navController: NavHostController){

    val name = rememberSaveable{ mutableStateOf("") }
    val isNameError = rememberSaveable { mutableStateOf(false) }
    val email = rememberSaveable{ mutableStateOf("") }
    val isEmailError = rememberSaveable { mutableStateOf(false) }
    val number = rememberSaveable{ mutableStateOf("") }
    val isNumberError = rememberSaveable { mutableStateOf(false) }

    val imageUri = rememberSaveable{ mutableStateOf<Uri?>(null) }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        imageUri.value = it
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "MasterAnd",
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .padding(bottom = 48.dp)
            )
        ProfileImageWithPicker(profileImageUri = imageUri.value, selectImageOnClick = {
            photoPicker
                .launch(PickVisualMediaRequest(ActivityResultContracts
                    .PickVisualMedia
                    .ImageOnly))
        })
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextFieldWithError(
            label = "Enter Name",
            supportingText = "Name can't be empty",
            validation = { text: String -> text.isNotEmpty() },
            text = name,
            isError = isNameError
        )
        OutlinedTextFieldWithError(
            label = "Enter email",
            supportingText = "Invalid email",
            validation = { text: String -> text.isNotEmpty() && EMAIL_ADDRESS.matcher(text).matches() },
            text = email,
            isError = isEmailError
        )
        OutlinedTextFieldWithError(
            label = "Enter number of colors",
            supportingText = "Invalid number. Enter number between 4 and 7.",
            validation = { text: String -> text.isNotEmpty() && text.isDigitsOnly() && text.toInt() >= 4 && text.toInt() <= 7 },
            text = number,
            isError = isNumberError
        )
        Button(
            onClick = {
                if ( validate(text = name.value, isError = isNameError, validator = { text: String -> text.isNotEmpty() }) &&
                    validate(text = email.value, isError = isEmailError, validator = { text: String -> text.isNotEmpty() && EMAIL_ADDRESS.matcher(text).matches() }) &&
                    validate(text = number.value, isError = isNumberError, validator = { text: String -> text.isNotEmpty() && text.isDigitsOnly() && text.toInt() >= 4 && text.toInt() <= 7})
                    ){
                    navController.navigate(route = Screen.Profile.route + "?uri=${imageUri.value}&username=${name.value}&email=${email.value}&number=${number.value}")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("Next")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedTextFieldWithError(label: String, supportingText: String, validation: (String)->Boolean,
                               text: MutableState<String>, isError: MutableState<Boolean>){

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = text.value,
        onValueChange = { text.value = it },
        label = { Text(text = label)},
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            disabledBorderColor = if (isError.value) Color.Red else Color.Gray,
            disabledLabelColor = if (isError.value) Color.Red else Color.Gray,
            disabledSupportingTextColor = if (isError.value) Color.Red else Color.Gray,
        ),
        isError = isError.value,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        supportingText = { if (!validation(text.value) || isError.value) {
            Text(text = supportingText)
            isError.value = true
        } else {
            Text(text = "")
            isError.value = false
        }
        }
    )
}

@Composable
@Preview
fun ProfileImageWithPicker() {
    val imageUri = rememberSaveable{ mutableStateOf<Uri?>(null) }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        imageUri.value = it
    }

    ProfileImageWithPicker(profileImageUri = imageUri.value, selectImageOnClick = {
        photoPicker
            .launch(PickVisualMediaRequest(ActivityResultContracts
                .PickVisualMedia
                .ImageOnly))
    })

}

@Composable
private fun ProfileImageWithPicker(profileImageUri: Uri?, selectImageOnClick: () -> Unit){
    Box{
        //wymaga dodania ikony w katalogu /res/drawable
        //(prawy przycisk | New | Vector asset)
        if (profileImageUri == null){
            Box {
                Image(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .align(Alignment.Center)
                        .padding(20.dp),
                    painter = painterResource(id = R.drawable.baseline_question_mark_24),
                    contentDescription = "Profile photo",
                    contentScale = ContentScale.Crop,
                )
                IconButton(onClick = selectImageOnClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)) {
                    Icon(painter = painterResource(id = R.drawable.baseline_image_search_24),
                        contentDescription = "Image selector")
                }
            }
        } else {
            Box {
                AsyncImage(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .align(Alignment.Center)
                        .padding(20.dp),
                    model = profileImageUri,
                    contentDescription = "Profile photo",
                    contentScale = ContentScale.Crop,
                )
                IconButton(onClick = selectImageOnClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_image_search_24),
                        contentDescription = "Image selector"
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview(){

    val navHostController = rememberNavController()

    LoginScreen(navController = navHostController)
}

private fun validate(text: String, isError: MutableState<Boolean>, validator: (String) -> Boolean) : Boolean {

    isError.value = !validator(text)

    return !isError.value
}