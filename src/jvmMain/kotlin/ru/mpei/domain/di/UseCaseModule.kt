package ru.mpei.domain.di

import org.koin.dsl.module
import ru.mpei.domain.usecases.FileUseCases
import ru.mpei.domain.usecases.KeyStoreUseCases
import ru.mpei.domain.usecases.KeyUseCases

val useCaseModule = module {

    factory {
        FileUseCases(fileRepository = get(), keyRepository = get(), keyStoreRepository = get())
    }

    factory {
        KeyUseCases(keyRepository = get())
    }

    factory {
        KeyStoreUseCases(keyStoreRepository = get())
    }
}