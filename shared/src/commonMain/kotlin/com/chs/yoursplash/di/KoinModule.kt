package com.chs.yoursplash.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.ksp.generated.defaultModule
import org.koin.ksp.generated.module


fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            platformModule(),
            SourceModule().module,
            defaultModule,
        )
    }
}
