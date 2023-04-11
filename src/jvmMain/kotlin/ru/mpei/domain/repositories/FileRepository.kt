package ru.mpei.domain.repositories

import java.io.File
import java.security.PrivateKey
import java.security.PublicKey
import ru.mpei.domain.models.FileModel

interface FileRepository {

    suspend fun saveFile(file: File, nickName: String, data: ByteArray, privateKey: PrivateKey)

    suspend fun readFile(file: File, publicKey: PublicKey): FileModel

    suspend fun chooseFile(): File
}