package ru.mpei.data.di

import org.koin.dsl.module
import ru.mpei.data.mappers.FileMapper
import ru.mpei.data.mappers.IntToByteMapper
import ru.mpei.data.mappers.ExternalKeyMapper
import ru.mpei.data.mappers.InternalKeyMapper

val mapperModule = module {

    factory {
        FileMapper(intToByteMapper = get())
    }

    factory {
        IntToByteMapper()
    }

    factory {
        ExternalKeyMapper(intToByteMapper = get())
    }

    factory {
        InternalKeyMapper(intToByteMapper = get())
    }
}