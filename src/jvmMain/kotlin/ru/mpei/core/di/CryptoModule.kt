package ru.mpei.core.di

import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.SecureRandom
import java.security.Signature
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.koin.core.qualifier.named
import org.koin.dsl.module

val cryptoModule = module {

    factory(qualifier = named("key")) {
        Signature.getInstance("RIPEMD160withRSA", BouncyCastleProvider())
    }

    factory(qualifier = named("file")) {
        Signature.getInstance("SHA1WithDSA")
    }

    single {
        SecureRandom()
    }

    single(qualifier = named("DSA")) {
        val keyPairGenerator = KeyPairGenerator.getInstance("DSA")
        keyPairGenerator.initialize(1024)
        keyPairGenerator
    }

    single(qualifier = named("RSA")) {
        KeyPairGenerator.getInstance("RSA")
    }

    single {
        KeyStore.getInstance("PKCS12")
    }

    single {
        KeyFactory.getInstance("DSA")
    }
}