package ru.mpei.domain.models

data class FileModel(
    val nickName: String,
    val data: ByteArray,
    val signature: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FileModel

        if (nickName != other.nickName) return false
        if (!data.contentEquals(other.data)) return false
        if (!signature.contentEquals(other.signature)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = nickName.hashCode()
        result = 31 * result + data.contentHashCode()
        result = 31 * result + signature.contentHashCode()
        return result
    }
}