package ru.mpei.data.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
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

    factory<CryptoRepository> {
        CryptoRepositoryImpl(
            keySignature = get(qualifier = named("key")),
            fileSignature = get(qualifier = named("file")),
            secureRandom = get(),
            keyFactory = get()
        )
    }

    factoryOf(::FileRepositoryImpl) {
        bind<FileRepository>()
    }

    factoryOf(::KeyStoreRepositoryImpl) {
        bind<KeyStoreRepository>()
    }

    factory<KeyRepository> {
        KeyRepositoryImpl(
            keyStoreRepository = get(),
            keyPairGeneratorDSA = get(qualifier = named(name = "DSA")),
            keyPairGeneratorRSA = get(qualifier = named(name = "RSA")),
            externalKeyMapper = get(),
            internalKeyMapper = get(),
            cryptoRepository = get()
        )
    }
}