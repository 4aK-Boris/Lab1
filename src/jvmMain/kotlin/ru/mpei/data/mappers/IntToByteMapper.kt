package ru.mpei.data.mappers

class IntToByteMapper {

    fun map(value: Int): ByteArray {
        val list = mutableListOf<Byte>()
        var number = value
        while (number > 127) {
            val count = (number % 128).toByte()
            println(count)
            list.add(count)
            number /= 128
        }
        list.add(number.toByte())
        list.reverse()
        while (list.size < 3) {
            list.add(index = 0, element = 0)
        }
        return list.toByteArray()
    }

    fun map(value: ByteArray): Int {
        val list = value.reversed()
        var number = 0
        for (i in list.indices) {
            number += pow(n = i) * list[i]
        }
        return number
    }

    private fun pow(n: Int): Int {
        if (n == 0) return 1
        return 128 * pow(n = n - 1)
    }
}