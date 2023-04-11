package ru.mpei.data.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.mpei.data.repositories.CryptoRepositoryImpl
import ru.mpei.data.repositories.FileRepositoryImpl
import ru.mpei.data.repositories.KeyRepositoryImpl
import ru.mpei.data.repositories.KeyStoreRepositoryImpl
import ru.mpei.domain.repositories.CryptoRepository
import ru.mpei.domain.repositories.FileRepository
import ru.mpei.domain.repositories.KeyRepository
import ru.mpei.domain.repositories.KeyStoreRepository

val repositoriesModule = module {

    factoryOf(::CryptoRepositoryImpl) {
        bind<CryptoRepository>()
    }

    factoryOf(::FileRepositoryImpl) {
        bind<FileRepository>()
    }

    factoryOf(::KeyStoreRepositoryImpl) {
        bind<KeyStoreRepository>()
    }

    factoryOf(::KeyRepositoryImpl) {
        bind<KeyRepository>()
    }
}