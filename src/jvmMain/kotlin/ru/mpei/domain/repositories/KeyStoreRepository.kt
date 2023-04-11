package ru.mpei.domain.repositories

import java.security.KeyPair
import java.security.PrivateKey
import java.security.PublicKey

interface KeyStoreRepository {

    suspend fun load()

    suspend fun save()

    suspend fun addKeyPair(keyPair: KeyPair, nickName: String)

    suspend fun getPrivateKey(nickName: String): PrivateKey

    suspend fun getPublicKey(nickName: String): PublicKey

    suspend fun deleteKeyPair(nickName: String)
}