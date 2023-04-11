package ru.mpei.data.repositories

import java.io.File
import java.security.KeyPairGenerator
import java.security.PublicKey
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter
import ru.mpei.core.Lab1Exception
import ru.mpei.data.dto.KeyDTO
import ru.mpei.data.mappers.ExternalKeyMapper
import ru.mpei.data.mappers.InternalKeyMapper
import ru.mpei.domain.models.ExternalKeyModel
import ru.mpei.domain.models.InternalKeyModel
import ru.mpei.domain.repositories.CryptoRepository
import ru.mpei.domain.repositories.KeyRepository
import ru.mpei.domain.repositories.KeyStoreRepository


class KeyRepositoryImpl(
    private val keyStoreRepository: KeyStoreRepository,
    private val keyPairGenerator: KeyPairGenerator,
    private val externalKeyMapper: ExternalKeyMapper,
    private val internalKeyMapper: InternalKeyMapper,
    private val cryptoRepository: CryptoRepository
) : KeyRepository {

    override suspend fun exportKeyFromKeyStore(path: String, nickName: String) {
        val key = keyStoreRepository.getPublicKey(nickName = nickName)
        val keyModel = ExternalKeyModel(nickName = nickName, key = key.encoded)
        writeExternalKey(path = path, keyModel = keyModel)
    }

    override suspend fun exportKey(nickName: String, path: String) {
        val internalKeyModel = readInternalKey(nickName = nickName)
        val publicKey = keyStoreRepository.getPublicKey(nickName = nickName)
        val verify = cryptoRepository.verifySignature(
            data = internalKeyModel.key,
            sign = internalKeyModel.signature,
            publicKey = publicKey
        )
        if (!verify) throw Lab1Exception("Цифровая подпись под коючом пользователя $nickName не прошла проверку")
        val externalKeyModel = ExternalKeyModel(nickName = internalKeyModel.nickName, key = internalKeyModel.key)
        writeExternalKey(keyModel = externalKeyModel, path)
    }

    override suspend fun getPublicKey(file: File): PublicKey {
        val keyModel = readInternalKey(file = file)
        return cryptoRepository.createPublicKey(key = keyModel.key)
    }

    override suspend fun importKey(file: File, nickName: String) {
        val privateKey = keyStoreRepository.getPrivateKey(nickName = nickName)
        val keyModel = if (file.name.endsWith(".SD")) {
            val internalKeyModel = readInternalKey(file = file)
            val signature = cryptoRepository.createSignature(data = internalKeyModel.key, privateKey = privateKey)
            internalKeyModel.copy(signature = signature)
        } else  {
            val externalKeyModel = readExternalKey(file = file)
            val signature = cryptoRepository.createSignature(data = externalKeyModel.key, privateKey = privateKey)
            InternalKeyModel(nickName = externalKeyModel.nickName, key = externalKeyModel.key, signature = signature)
        }
        writeInternalKey(keyModel = keyModel)
    }

    override suspend fun createKeyPair(nickName: String) {
        val keyPair = keyPairGenerator.generateKeyPair()
        keyStoreRepository.addKeyPair(keyPair = keyPair, nickName = nickName)
        keyStoreRepository.save()
    }

    override suspend fun deleteKeyPair(nickName: String) {
        keyStoreRepository.deleteKeyPair(nickName = nickName)
        keyStoreRepository.save()
    }

    override suspend fun chooseFile(): File {
        val chooser = JFileChooser(path)
        chooser.fileSelectionMode = JFileChooser.FILES_ONLY
        chooser.fileFilter = FileNameExtensionFilter("Pub файл", "pub")
        val result = chooser.showSaveDialog(null)
        if (result != JFileChooser.APPROVE_OPTION) throw Lab1Exception("Не удалось выбрать файл!")
        return chooser.selectedFile
    }

    override suspend fun chooseDirectory(): File {
        val chooser = JFileChooser(path)
        chooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        val result = chooser.showSaveDialog(null)
        if (result != JFileChooser.APPROVE_OPTION) throw Lab1Exception("Не удалось выбрать папку!")
        return chooser.selectedFile
    }

    override suspend fun chooseKey(): File {
        val chooser = JFileChooser("$path/PK")
        chooser.fileSelectionMode = JFileChooser.FILES_ONLY
        chooser.fileFilter = FileNameExtensionFilter("PK файл", "pk")
        val result = chooser.showSaveDialog(null)
        if (result != JFileChooser.APPROVE_OPTION) throw Lab1Exception("Не удалось выбрать файл!")
        return chooser.selectedFile
    }

    private fun writeExternalKey(keyModel: ExternalKeyModel, path: String) {
        val keyDTO = externalKeyMapper.map(keyModel = keyModel)
        val file = File("$path/${keyModel.nickName}.pub")
        file.writeBytes(keyDTO.key)
    }

    private fun writeInternalKey(keyModel: InternalKeyModel) {
        val keyDTO = internalKeyMapper.map(keyModel = keyModel)
        val file = File("$pathPK/${keyModel.nickName}.pk")
        file.writeBytes(keyDTO.key)
    }

    private fun readExternalKey(file: File): ExternalKeyModel {
        val keyDTO = KeyDTO(key = file.readBytes())
        return externalKeyMapper.map(keyDTO = keyDTO)
    }

    private fun readInternalKey(nickName: String): InternalKeyModel {
        val file = File("$pathPK/$nickName.PK")
        return readInternalKey(file = file)
    }

    private fun readInternalKey(file: File): InternalKeyModel {
        val keyDTO = KeyDTO(key = file.readBytes())
        return internalKeyMapper.map(keyDTO = keyDTO)
    }

    companion object {
        private val path = System.getProperty("user.dir")
        private val pathPK = "$path/PK"
    }
}