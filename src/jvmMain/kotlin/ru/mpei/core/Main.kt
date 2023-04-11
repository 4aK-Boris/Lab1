package ru.mpei.core

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.koin.compose.koinInject
import org.koin.core.context.GlobalContext.startKoin
import ru.mpei.core.di.appModule
import ru.mpei.presentation.ui.MainScreen
import ru.mpei.presentation.viewmodels.MainViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        MainScreen()
    }
}

fun main() = run {

    startKoin {
        modules(appModule)
    }

    application {

        val viewModel = koinInject<MainViewModel>()

        val title by viewModel.title.collectAsState()

        Window(onCloseRequest = ::exitApplication, title = title, icon = painterResource(resourcePath = "mpei.jpg")) {
            App()
        }
    }
}

