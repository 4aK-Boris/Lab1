package ru.mpei.data.mappers

import ru.mpei.data.dto.FileDTO
import ru.mpei.domain.models.FileModel

class FileMapper(
    private val intToByteMapper: IntToByteMapper
) {

    fun map(fileModel: FileModel): FileDTO {
        val nickNameLength = fileModel.nickName.length
        val nickNameLengthByte = intToByteMapper.map(value = nickNameLength)
        val signatureLength = fileModel.signature.size
        val signatureLengthByte = intToByteMapper.map(value = signatureLength)
        val nickNameByte = fileModel.nickName.encodeToByteArray()
        val file = nickNameLengthByte + signatureLengthByte + nickNameByte + fileModel.signature + fileModel.data
        return FileDTO(file = file)
    }

    fun map(fileDTO: FileDTO): FileModel {
        val nickNameLengthByte = fileDTO.file.copyOfRange(fromIndex = 0, toIndex = 3)
        val nickNameLength = intToByteMapper.map(value = nickNameLengthByte)
        val signatureLengthByte = fileDTO.file.copyOfRange(fromIndex = 3, toIndex = 6)
        val signatureLength =  intToByteMapper.map(value = signatureLengthByte)
        val nickName = fileDTO.file.copyOfRange(fromIndex = 6, toIndex = 6 + nickNameLength).decodeToString()
        val signature = fileDTO.file.copyOfRange(fromIndex = 6 + nickNameLength, toIndex = 6 + nickNameLength + signatureLength)
        val data = fileDTO.file.copyOfRange(fromIndex = 6 + nickNameLength + signatureLength, toIndex = fileDTO.file.size)
        return FileModel(nickName = nickName, data = data, signature = signature)
    }
}