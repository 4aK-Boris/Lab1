package ru.mpei.domain.repositories

import java.security.PrivateKey
import java.security.PublicKey

interface CryptoRepository {

    fun createKeySignature(data: ByteArray, privateKey: PrivateKey): ByteArray

    fun verifyKeySignature(data: ByteArray, sign: ByteArray, publicKey: PublicKey): Boolean

    fun createFileSignature(data: ByteArray, privateKey: PrivateKey): ByteArray

    fun verifyFileSignature(data: ByteArray, sign: ByteArray, publicKey: PublicKey): Boolean

    fun createPublicKey(key: ByteArray): PublicKey
}