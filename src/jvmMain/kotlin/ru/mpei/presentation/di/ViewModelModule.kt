package ru.mpei.presentation.di

import org.koin.dsl.module
import ru.mpei.presentation.viewmodels.MainViewModel

val viewModelModule = module {

    single {
        MainViewModel(fileUseCases = get(), keyUseCases = get(), keyStoreUseCases = get())
    }
}