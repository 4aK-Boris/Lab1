package ru.mpei.domain.repositories

import java.security.PrivateKey
import java.security.PublicKey

interface CryptoRepository {

    fun createMessageDigest(data: ByteArray): ByteArray

    fun verifyMessageDigest(data: ByteArray, hash: ByteArray): Boolean

    fun createSignature(data: ByteArray, privateKey: PrivateKey): ByteArray

    fun verifySignature(data: ByteArray, sign: ByteArray, publicKey: PublicKey): Boolean

    fun createPublicKey(key: ByteArray): PublicKey
}