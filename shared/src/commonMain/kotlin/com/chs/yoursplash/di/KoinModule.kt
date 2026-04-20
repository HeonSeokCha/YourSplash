package com.chs.yoursplash.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.ksp.generated.*


fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            platformModule,
            DomainModule().module,
            DataModule().module,
            ViewModelModule().module,
        )
    }
}
