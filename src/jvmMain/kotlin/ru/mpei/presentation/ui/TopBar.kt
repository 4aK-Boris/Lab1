package ru.mpei.presentation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.koinInject
import ru.mpei.presentation.viewmodels.MainViewModel

@Composable
fun TopBar(viewModel: MainViewModel = koinInject()) {

    Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.border(border = BorderStroke(width = 2.dp, color = Color.Black), shape = RectangleShape).height(height = 36.dp)) {

        TopBarFileButton(viewModel = viewModel)

        Divider(modifier = Modifier.width(width = 2.dp).height(height = 36.dp), color = Color.Black)

        TopBarKeyButton(viewModel = viewModel)
    }
}

@Composable
fun TopBarFileButton(viewModel: MainViewModel) {

    val expanded by viewModel.fileExpanded.collectAsState()

    Box(modifier = Modifier.height(height = 36.dp)) {
        TopBarButton(
            onClick = { viewModel.onFileExpandedChanged(value = true) },
            name = "Файл"
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { viewModel.onFileExpandedChanged(value = false) }
        ) {
            TopBarText(text = "Создать", onClick = viewModel::createFile)
            TopBarText(text = "Загрузить", onClick = viewModel::downloadFile)
            TopBarText(text = "Сохранить", onClick = viewModel::saveFile)
            Divider()
            TopBarText(text = "Выход", onClick = viewModel::exit)
            Divider()
            TopBarText(text = "О программе", onClick = viewModel::aboutProgram)
        }
    }
}

@Composable
fun TopBarKeyButton(viewModel: MainViewModel) {

    val expanded by viewModel.keyExpanded.collectAsState()

    Box {
        TopBarButton(
            onClick = { viewModel.onKeyExpandedChanged(value = true) },
            name = "Управление ключами"
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { viewModel.onKeyExpandedChanged(value = false) }
        ) {
            TopBarText(text = "Экспорт открытого ключа", onClick = viewModel::exportPublicKey)
            TopBarText(text = "Импорт открытого ключа", onClick = viewModel::importPublicKey)
            TopBarText(text = "Удаление пары ключей", onClick = viewModel::deleteKeyPair)
            TopBarText(text = "Выбор закрытого ключа", onClick = viewModel::chooseUser)
            TopBarText(text = "Создание пары ключей", onClick = viewModel::createKeyPair)
            TopBarText(text = "Экспорт открытого ключа их хранилища", onClick = viewModel::exportPublicKeyFromKeyStore)
        }
    }
}

@Composable
fun TopBarText(text: String, onClick: () -> Unit) {
    Text(text = text, fontSize = 18.sp, modifier = Modifier.padding(10.dp).clickable(onClick = onClick))
}

@Composable
fun TopBarButton(name: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
        shape = RectangleShape,
        modifier = Modifier.fillMaxHeight()
    ) {
        Text(text = name)
    }
}