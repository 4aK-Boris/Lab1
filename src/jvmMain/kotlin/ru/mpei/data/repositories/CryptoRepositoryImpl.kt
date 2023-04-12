package ru.mpei.data.repositories

import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.SecureRandom
import java.security.Signature
import java.security.spec.X509EncodedKeySpec
import ru.mpei.domain.repositories.CryptoRepository

class CryptoRepositoryImpl(
    private val keySignature: Signature,
    private val fileSignature: Signature,
    private val secureRandom: SecureRandom,
    private val keyFactory: KeyFactory
): CryptoRepository {
    override fun createKeySignature(data: ByteArray, privateKey: PrivateKey): ByteArray {
        keySignature.initSign(privateKey, secureRandom)
        keySignature.update(data)
        return keySignature.sign()
    }

    override fun verifyKeySignature(data: ByteArray, sign: ByteArray, publicKey: PublicKey): Boolean {
        keySignature.initVerify(publicKey)
        keySignature.update(data)
        return keySignature.verify(sign)
    }

    override fun createFileSignature(data: ByteArray, privateKey: PrivateKey): ByteArray {
        fileSignature.initSign(privateKey, secureRandom)
        fileSignature.update(data)
        return fileSignature.sign()
    }

    override fun verifyFileSignature(data: ByteArray, sign: ByteArray, publicKey: PublicKey): Boolean {
        fileSignature.initVerify(publicKey)
        fileSignature.update(data)
        return fileSignature.verify(sign)
    }

    override fun createPublicKey(key: ByteArray): PublicKey {
        return keyFactory.generatePublic(X509EncodedKeySpec(key))
    }
}