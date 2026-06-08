package com.chs.yoursplash.di

import org.koin.core.annotation.Configuration
import org.koin.core.annotation.KoinApplication
import org.koin.dsl.KoinAppDeclaration
import org.koin.plugin.module.dsl.startKoin

@KoinApplication(
    modules = [
        DomainModule::class,
        DataModule::class,
        ViewModelModule::class,
        PlatformModule::class
    ]
)
@Configuration
class KoinModule

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin<KoinModule> {
        config?.invoke(this)
    }
}
