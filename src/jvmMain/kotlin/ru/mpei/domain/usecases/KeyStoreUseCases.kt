package ru.mpei.domain.usecases

import ru.mpei.domain.repositories.KeyStoreRepository

class KeyStoreUseCases(
    private val keyStoreRepository: KeyStoreRepository
) {

    suspend fun loadKeyStore() {
        keyStoreRepository.load()
    }
}