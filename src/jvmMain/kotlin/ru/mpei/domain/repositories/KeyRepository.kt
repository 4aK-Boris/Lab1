package ru.mpei.domain.repositories

import java.io.File
import java.security.PublicKey

interface KeyRepository {

    suspend fun exportKeyFromKeyStore(path: String, nickName: String)

    suspend fun exportKey(nickName: String, path: String)

    suspend fun importKey(file: File, nickName: String)

    suspend fun getPublicKey(file: File): PublicKey

    suspend fun createKeyPair(nickName: String)

    suspend fun deleteKeyPair(nickName: String)

    suspend fun chooseFile(): File

    suspend fun chooseDirectory(): File

    suspend fun chooseKey(): File
}