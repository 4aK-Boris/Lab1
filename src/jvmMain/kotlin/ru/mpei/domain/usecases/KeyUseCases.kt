package ru.mpei.domain.usecases

import ru.mpei.core.Lab1Exception
import ru.mpei.domain.repositories.KeyRepository

class KeyUseCases(
    private val keyRepository: KeyRepository
) {

    suspend fun importKey(nickName: String) {
        checkNickName(nickName = nickName)
        val file = keyRepository.chooseFile()
        keyRepository.importKey(file = file, nickName = nickName)
    }

    suspend fun createKeyPair(nickName: String) {
        checkNickName(nickName = nickName)
        keyRepository.createKeyPair(nickName = nickName)
    }

    suspend fun deleteKeyPair(nickName: String) {
        checkNickName(nickName = nickName)
        keyRepository.deleteKeyPair(nickName = nickName)
    }

    suspend fun exportKey(nickName: String) {
        checkNickName(nickName = nickName)
        val file = keyRepository.chooseDirectory()
        keyRepository.exportKey(path = file.path, nickName = nickName)
    }

    suspend fun exportKeyFromKeyStore(nickName: String) {
        checkNickName(nickName = nickName)
        val file = keyRepository.chooseDirectory()
        keyRepository.exportKeyFromKeyStore(path = file.path, nickName = nickName)
    }

    private fun checkNickName(nickName: String) {
        if (nickName.isBlank()) throw Lab1Exception("Введите имя пользователя!")
    }
}