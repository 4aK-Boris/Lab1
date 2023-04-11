package ru.mpei.data.mappers

import ru.mpei.data.dto.KeyDTO
import ru.mpei.domain.models.InternalKeyModel

class InternalKeyMapper(
    private val intToByteMapper: IntToByteMapper
) {

    fun map(keyModel: InternalKeyModel): KeyDTO {
        val nickNameLength = keyModel.nickName.length
        val nickNameLengthByte = intToByteMapper.map(value = nickNameLength)
        val keyLength = keyModel.key.size
        val keyLengthByte = intToByteMapper.map(value = keyLength)
        val nickNameByteArray = keyModel.nickName.encodeToByteArray()
        val result = nickNameLengthByte + keyLengthByte + nickNameByteArray + keyModel.key + keyModel.signature
        return KeyDTO(key = result)
    }

    fun map(keyDTO: KeyDTO): InternalKeyModel {
        val nickNameLengthByte = keyDTO.key.copyOfRange(fromIndex = 0, toIndex = 3)
        val nickNameLength = intToByteMapper.map(value = nickNameLengthByte)
        val keyLengthByte = keyDTO.key.copyOfRange(fromIndex = 3, toIndex = 6)
        val keyLength = intToByteMapper.map(value = keyLengthByte)
        val nickNameByteArray = keyDTO.key.copyOfRange(fromIndex = 6, toIndex = 6 + nickNameLength)
        val key = keyDTO.key.copyOfRange(fromIndex = 6 + nickNameLength, toIndex = 6 + nickNameLength + keyLength)
        val signature = keyDTO.key.copyOfRange(fromIndex = 6 + nickNameLength + keyLength, toIndex = keyDTO.key.size)
        val nickName = nickNameByteArray.decodeToString()
        return InternalKeyModel(nickName = nickName, key = key, signature = signature)
    }
}