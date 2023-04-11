package ru.mpei.data.repositories

import java.security.KeyFactory
import java.security.MessageDigest
import java.security.PrivateKey
import java.security.PublicKey
import java.security.SecureRandom
import java.security.Signature
import java.security.spec.X509EncodedKeySpec
import ru.mpei.domain.repositories.CryptoRepository

class CryptoRepositoryImpl(
    private val messageDigest: MessageDigest,
    private val signature: Signature,
    private val secureRandom: SecureRandom,
    private val keyFactory: KeyFactory
): CryptoRepository {
    override fun createMessageDigest(data: ByteArray): ByteArray {
        return messageDigest.digest(data)
    }

    override fun verifyMessageDigest(data: ByteArray, hash: ByteArray): Boolean {
        return messageDigest.digest(data).contentEquals(hash)
    }

    override fun createSignature(data: ByteArray, privateKey: PrivateKey): ByteArray {
        signature.initSign(privateKey, secureRandom)
        signature.update(data)
        return signature.sign()
    }

    override fun verifySignature(data: ByteArray, sign: ByteArray, publicKey: PublicKey): Boolean {
        signature.initVerify(publicKey)
        signature.update(data)
        return signature.verify(sign)
    }

    override fun createPublicKey(key: ByteArray): PublicKey {
        return keyFactory.generatePublic(X509EncodedKeySpec(key))
    }
}