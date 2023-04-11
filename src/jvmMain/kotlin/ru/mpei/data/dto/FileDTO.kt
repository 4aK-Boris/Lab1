package ru.mpei.data.dto

data class FileDTO(val file: ByteArray) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FileDTO

        if (!file.contentEquals(other.file)) return false

        return true
    }

    override fun hashCode(): Int {
        return file.contentHashCode()
    }
}