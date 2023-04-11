package ru.mpei.domain.models

data class InternalKeyModel(
    val nickName: String,
    val key: ByteArray,
    val signature: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as InternalKeyModel

        if (nickName != other.nickName) return false
        if (!key.contentEquals(other.key)) return false
        if (!signature.contentEquals(other.signature)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = nickName.hashCode()
        result = 31 * result + key.contentHashCode()
        result = 31 * result + signature.contentHashCode()
        return result
    }
}