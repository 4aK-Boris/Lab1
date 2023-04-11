package ru.mpei.data.repositories

import java.io.File
import java.security.PrivateKey
import java.security.PublicKey
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter
import ru.mpei.core.Lab1Exception
import ru.mpei.data.dto.FileDTO
import ru.mpei.data.mappers.FileMapper
import ru.mpei.domain.models.FileModel
import ru.mpei.domain.repositories.CryptoRepository
import ru.mpei.domain.repositories.FileRepository

class FileRepositoryImpl(
    private val cryptoRepository: CryptoRepository,
    private val fileMapper: FileMapper
) : FileRepository {
    override suspend fun saveFile(file: File, nickName: String, data: ByteArray, privateKey: PrivateKey) {
        val signature = cryptoRepository.createSignature(data = data, privateKey = privateKey)
        val fileModel = FileModel(nickName = nickName, data = data, signature = signature)
        val fileDTO = fileMapper.map(fileModel = fileModel)
        file.writeBytes(fileDTO.file)
    }

    override suspend fun readFile(file: File, publicKey: PublicKey): FileModel {
        if (!file.exists()) throw Lab1Exception(message = "Не найден файл с именем ${file.name}")
        val fileDTO = FileDTO(file = file.readBytes())
        val fileModel = fileMapper.map(fileDTO = fileDTO)
        val result =
            cryptoRepository.verifySignature(data = fileModel.data, sign = fileModel.signature, publicKey = publicKey)
        if (!result) throw Lab1Exception("Подпись файла не прошла проверку!")
        return fileModel
    }

    override suspend fun chooseFile(): File {
        val chooser = JFileChooser(path)
        chooser.fileSelectionMode = JFileChooser.FILES_ONLY
        chooser.fileFilter = FileNameExtensionFilter("SD файл", "sd")
        val result = chooser.showSaveDialog(null)
        if (result != JFileChooser.APPROVE_OPTION) throw Lab1Exception("Не удалось выбрать файл!")
        return chooser.selectedFile
    }

    companion object {
        private val path = System.getProperty("user.dir")
    }
}