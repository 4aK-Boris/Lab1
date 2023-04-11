package ru.mpei.core.di

import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.MessageDigest
import java.security.SecureRandom
import java.security.Signature
import org.koin.dsl.module

val cryptoModule = module {

    factory {
        MessageDigest.getInstance("SHA-1")
    }

    factory {
        Signature.getInstance("SHA1WithDSA")
    }

    single {
        SecureRandom()
    }

    single {
        val keyPairGenerator = KeyPairGenerator.getInstance("DSA")
        keyPairGenerator.initialize(1024)
        keyPairGenerator
    }

    single {
        KeyStore.getInstance("PKCS12")
    }

    single {
        KeyFactory.getInstance("DSA")
    }
}