package ru.mpei.core.di

import org.koin.dsl.module
import ru.mpei.data.di.mapperModule
import ru.mpei.data.di.repositoriesModule
import ru.mpei.domain.di.useCaseModule
import ru.mpei.presentation.di.viewModelModule

val appModule = module {
    includes(viewModelModule, cryptoModule, repositoriesModule, mapperModule, useCaseModule)
}