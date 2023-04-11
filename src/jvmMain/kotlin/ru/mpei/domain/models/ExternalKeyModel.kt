package ru.mpei.domain.models

data class ExternalKeyModel(
    val nickName: String,
    val key: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExternalKeyModel

        if (nickName != other.nickName) return false
        if (!key.contentEquals(other.key)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = nickName.hashCode()
        result = 31 * result + key.contentHashCode()
        return result
    }
}