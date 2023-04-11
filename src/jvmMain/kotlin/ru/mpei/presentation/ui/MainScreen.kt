package ru.mpei.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import ru.mpei.presentation.viewmodels.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel = koinInject()) {

    val error by viewModel.error.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    ErrorDialog(error = error, errorMessage = errorMessage, close = viewModel::closeErrorDialog)

    LaunchedEffect(key1 = Unit) {
        viewModel.loadKeyStore()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        TopBar()

        MainView(viewModel = viewModel)

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ErrorDialog(error: Boolean, errorMessage: String, close: () -> Unit) {
    if (error) {
        AlertDialog(
            modifier = Modifier.width(240.dp),
            onDismissRequest = close,
            title = {
                Text(text = "Ошибка")
            },
            text = {
                Text(text = errorMessage)
            },
            confirmButton = {
                Button(onClick = close) {
                    Text(text = "ОК")
                }
            }
        )
    }
}

@Composable
fun MainView(viewModel: MainViewModel) {

    val nickName by viewModel.nickName.collectAsState()
    val text by viewModel.text.collectAsState()
    val enabled by viewModel.enabledNickName.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth().padding(all = 24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = nickName,
                onValueChange = viewModel::onNickNameChanged,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White
                ),
                shape = RectangleShape,
                trailingIcon = {
                    IconButton(onClick = viewModel::disableNickName) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = "Подтвердить")
                    }
                },
                label = {
                    Text(text = "Имя пользователя")
                }, enabled = enabled)

            Buttons(viewModel = viewModel)
        }

        Spacer(modifier = Modifier.height(height = 36.dp))

        TextField(
            value = text,
            onValueChange = viewModel::onTextChanged,
            singleLine = false,
            modifier = Modifier.fillMaxSize(),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White
            ),
            shape = RoundedCornerShape(size = 16.dp)
        )
    }
}

@Composable
fun Buttons(viewModel: MainViewModel) {
    Row(
        modifier = Modifier
            .width(width = 408.dp)
            .height(height = 48.dp)
            .clip(shape = AbsoluteCutCornerShape(percent = 50))
            .border(width = 2.dp, color = Color.Black, shape = AbsoluteCutCornerShape(percent = 50))
            .background(color = Color.LightGray)
    ) {

        Spacer(modifier = Modifier.width(width = 24.dp))

        MainButton(text = "Выбрать пользователя", onClick = viewModel::chooseUser)

        Divider(modifier = Modifier.fillMaxHeight().width(width = 2.dp), color = Color.Black)

        MainButton(text = "Загрузить документ", onClick = viewModel::downloadFile)

        Divider(modifier = Modifier.fillMaxHeight().width(width = 2.dp), color = Color.Black)

        MainButton(text = "Сохранить документ", onClick = viewModel::saveFile)

        Spacer(modifier = Modifier.width(width = 24.dp))
    }
}

@Composable
fun MainButton(text: String, onClick: () -> Unit) {

    IconButton(
        onClick = onClick,
        modifier = Modifier.fillMaxHeight().width(width = 120.dp)
    ) {
        Text(text = text, maxLines = 2, textAlign = TextAlign.Center)
    }
}