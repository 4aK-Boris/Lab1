package ru.mpei.data.repositories

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.math.BigInteger
import java.security.KeyPair
import java.security.KeyStore
import java.security.KeyStore.PasswordProtection
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Security
import java.security.cert.X509Certificate
import java.util.*
import org.bouncycastle.asn1.x500.X500Name
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder
import ru.mpei.core.Lab1Exception
import ru.mpei.domain.repositories.KeyStoreRepository

class KeyStoreRepositoryImpl(
    private val keyStore: KeyStore
) : KeyStoreRepository {

    override suspend fun load() {
        val file = File(KEY_STORE_NAME)
        if (file.exists()) {
            val keyStoreData = FileInputStream(KEY_STORE_NAME)
            keyStore.load(keyStoreData, password.toCharArray())
        } else {
            keyStore.load(null, password.toCharArray())
        }
    }

    override suspend fun save() {
        val file = File("$path/PK")
        if (!file.exists()) file.mkdirs()
        val keyStoreOutputStream = FileOutputStream(KEY_STORE_NAME)
        keyStore.store(keyStoreOutputStream, password.toCharArray())
    }

    override suspend fun addKeyPair(keyPair: KeyPair, nickName: String) {
        if (keyStore.aliases().toList().contains(nickName)) throw Lab1Exception("Пара ключей для пользователя $nickName есть уже в хранилище ключей")
        val certificate = createCertificate(keyPair = keyPair, nickName = nickName)
        val privateKeyEntry = KeyStore.PrivateKeyEntry(keyPair.private, arrayOf(certificate))
        val password = PasswordProtection(nickName.toCharArray())
        keyStore.setEntry(nickName, privateKeyEntry, password)
    }

    override suspend fun getPrivateKey(nickName: String): PrivateKey {
        if (!keyStore.aliases().toList().contains(nickName)) throw Lab1Exception("Закрытый ключ пользователя $nickName не был найден в хранилище")
        val password = PasswordProtection(nickName.toCharArray())
        val privateKeyEntry = keyStore.getEntry(nickName, password) as KeyStore.PrivateKeyEntry
        return privateKeyEntry.privateKey
    }

    override suspend fun getPublicKey(nickName: String): PublicKey {
        if (!keyStore.aliases().toList().contains(nickName)) throw Lab1Exception("Открытый ключ пользователя $nickName не был найден в хранилище")
        val password = PasswordProtection(nickName.toCharArray())
        val privateKeyEntry = keyStore.getEntry(nickName, password) as KeyStore.PrivateKeyEntry
        return privateKeyEntry.certificate.publicKey
    }

    override suspend fun deleteKeyPair(nickName: String) {
        keyStore.deleteEntry(nickName)
    }

    private val startDate: Date
        get() = Date()

    private val endDate: Date
        get() {
            Calendar.getInstance().apply {
                time = startDate
                add(Calendar.YEAR, 1)
                return this.time
            }
        }

    private val serialNumber: BigInteger
        get() {
            val now = System.currentTimeMillis()
            return BigInteger(now.toString())
        }

    private fun createCertificate(keyPair: KeyPair, nickName: String): X509Certificate {
        Security.addProvider(bcProvider)
        val x500Name = X500Name("CN=$nickName")
        val certificateBuilder = JcaX509v3CertificateBuilder(x500Name, serialNumber, startDate, endDate, x500Name, keyPair.public)
        val contentSigner = JcaContentSignerBuilder("SHA1WithDSA").build(keyPair.private)
        val x509CertificateHolder = certificateBuilder.build(contentSigner)
        return JcaX509CertificateConverter().setProvider(bcProvider).getCertificate(x509CertificateHolder)
    }

    companion object {

        private val path = System.getProperty("user.dir")

        private val KEY_STORE_NAME = "$path/PK/keystore.jks"

        private val bcProvider = BouncyCastleProvider()

        private const val password = "123456"
    }
}