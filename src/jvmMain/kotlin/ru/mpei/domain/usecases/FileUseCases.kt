package ru.mpei.domain.usecases

import ru.mpei.core.Lab1Exception
import ru.mpei.domain.models.FileModel
import ru.mpei.domain.repositories.FileRepository
import ru.mpei.domain.repositories.KeyRepository
import ru.mpei.domain.repositories.KeyStoreRepository

class FileUseCases(
    private val fileRepository: FileRepository,
    private val keyRepository: KeyRepository,
    private val keyStoreRepository: KeyStoreRepository
) {

    suspend fun downloadUseCase(): FileModel {
        val file = fileRepository.chooseFile()
        val keyFile = keyRepository.chooseKey()
        val publicKey = keyRepository.getPublicKey(file = keyFile)
        return fileRepository.readFile(file = file, publicKey = publicKey)
    }

    suspend fun saveFileUseCase(nickName: String, text: String) {
        checkNickName(nickName = nickName)
        val file = fileRepository.chooseFile()
        val privateKey = keyStoreRepository.getFilePrivateKey(nickName = nickName)
        fileRepository.saveFile(
            nickName = nickName,
            data = text.encodeToByteArray(),
            privateKey = privateKey,
            file = file
        )
    }

    private fun checkNickName(nickName: String) {
        if (nickName.isBlank()) throw Lab1Exception("Введите имя пользователя!")
    }
}