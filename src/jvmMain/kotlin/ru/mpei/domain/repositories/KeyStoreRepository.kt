package ru.mpei.domain.repositories

import java.security.KeyPair
import java.security.PrivateKey
import java.security.PublicKey

interface KeyStoreRepository {

    suspend fun load()

    suspend fun save()

    suspend fun addFileKeyPair(keyPair: KeyPair, nickName: String)

    suspend fun addKeyKeyPair(keyPair: KeyPair, nickName: String)

    suspend fun getKeyPrivateKey(nickName: String): PrivateKey

    suspend fun getFilePrivateKey(nickName: String): PrivateKey
    suspend fun getKeyPublicKey(nickName: String): PublicKey

    suspend fun getFilePublicKey(nickName: String): PublicKey

    suspend fun deleteKeyPair(nickName: String)
}